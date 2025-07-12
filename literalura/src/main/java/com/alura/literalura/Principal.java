package com.alura.literalura;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final LibroService libroService;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroService libroService, LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroService = libroService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) {
        mostrarMenu();
    }

    public void mostrarMenu() {
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n----- MENÚ -----");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un año específico");
            System.out.println("5 - Buscar libros por idioma");
            System.out.println("0 - Salir");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el título del libro: ");
                    String titulo = teclado.nextLine();
                    libroService.buscarYGuardarLibro(titulo);
                    break;
                case 2:
                    List<Libro> libros = libroRepository.findAll();
                    if (libros.isEmpty()) {
                        System.out.println("No hay libros registrados.");
                    } else {
                        for (Libro libro : libros) {
                            System.out.println("\nTítulo: " + libro.getTitulo());
                            System.out.println("Idioma: " + libro.getIdioma());
                            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                            Autor autor = libro.getAutor();
                            System.out.println("Autor: " + autor.getNombre());
                            System.out.println("Año de nacimiento: " + autor.getAnoNacimiento());
                            System.out.println("Año de muerte: " + autor.getAnoMuerte());
                        }
                    }
                    break;
                case 3:
                    List<Autor> autores = autorRepository.findAll();
                    if (autores.isEmpty()) {
                        System.out.println("No hay autores registrados.");
                    } else {
                        for (Autor autor : autores) {
                            System.out.println("\nAutor: " + autor.getNombre());
                            System.out.println("Año de nacimiento: " + autor.getAnoNacimiento());
                            System.out.println("Año de muerte: " + autor.getAnoMuerte());
                        }
                    }
                    break;
                case 4:
                    System.out.print("Ingresa el año: ");
                    int ano = teclado.nextInt();
                    teclado.nextLine();
                    List<Autor> autoresVivos = autorRepository.findByAnoNacimientoLessThanEqualAndAnoMuerteGreaterThanEqualOrAnoMuerteIsNull(ano, ano);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("No hay autores vivos en ese año.");
                    } else {
                        for (Autor autor : autoresVivos) {
                            System.out.println("Autor: " + autor.getNombre());
                        }
                    }
                    break;
                case 5:
                    System.out.print("Ingresa el idioma (por ejemplo, 'en' o 'es'): ");
                    String idioma = teclado.nextLine();
                    List<Libro> librosPorIdioma = libroRepository.findByIdiomaIgnoreCase(idioma);
                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("No se encontraron libros en ese idioma.");
                    } else {
                        for (Libro libro : librosPorIdioma) {
                            System.out.println("\nTítulo: " + libro.getTitulo());
                            System.out.println("Idioma: " + libro.getIdioma());
                            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                            System.out.println("Autor: " + libro.getAutor().getNombre());
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }

        } while (opcion != 0);
    }
}

