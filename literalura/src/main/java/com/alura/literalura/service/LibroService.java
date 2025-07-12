package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

    private final GutendexClient client;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(GutendexClient client, LibroRepository libroRepository, AutorRepository autorRepository) {
        this.client = client;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarYGuardarLibro(String titulo) {
        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println(" El libro ya está registrado.");
            return;
        }

        JsonObject respuesta = client.buscarLibroPorTitulo(titulo);
        JsonArray resultados = respuesta.getAsJsonArray("results");

        if (resultados.size() == 0) {
            System.out.println(" Libro no encontrado.");
            return;
        }

        JsonObject resultado = resultados.get(0).getAsJsonObject();
        String tituloLibro = resultado.get("title").getAsString();
        String idioma = resultado.getAsJsonArray("languages").get(0).getAsString();
        int descargas = resultado.get("download_count").getAsInt();

        JsonObject autorJson = resultado.getAsJsonArray("authors").get(0).getAsJsonObject();
        String nombreAutor = autorJson.get("name").getAsString();
        Integer nacimiento = autorJson.get("birth_year").isJsonNull() ? null : autorJson.get("birth_year").getAsInt();
        Integer muerte = autorJson.get("death_year").isJsonNull() ? null : autorJson.get("death_year").getAsInt();

        Autor autor = autorRepository.findByNombreIgnoreCase(nombreAutor)
                .orElseGet(() -> autorRepository.save(new Autor(nombreAutor, nacimiento, muerte)));

        Libro libro = new Libro(tituloLibro, idioma, descargas, autor);
        libroRepository.save(libro);

        System.out.println(" Libro guardado correctamente:");
        System.out.println(" Título: " + tituloLibro);
        System.out.println(" Autor: " + nombreAutor);
        System.out.println(" Idioma: " + idioma);
        System.out.println(" Descargas: " + descargas);
        System.out.println(" Año de nacimiento del autor: " + (nacimiento != null ? nacimiento : "Desconocido"));
        System.out.println(" Año de muerte del autor: " + (muerte != null ? muerte : "Desconocido"));
    }
}
