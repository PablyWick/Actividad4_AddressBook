import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.filePath = filePath;
        contacts = new HashMap<>();
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String number = parts[0];
                    String name = parts[1];
                    contacts.put(number, name);
                }
            }
            System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String number = entry.getKey();
                String name = entry.getValue();
                writer.write(number + "," + name);
                writer.newLine();
            }
            System.out.println("Cambios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String number = entry.getKey();
            String name = entry.getValue();
            System.out.println(number + " : " + name);
        }
    }

    public void create() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número telefónico: ");
        String number = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String name = scanner.nextLine();
        contacts.put(number, name);
        System.out.println("Contacto creado correctamente.");
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número telefónico a eliminar: ");
        String number = scanner.nextLine();
        if (contacts.containsKey(number)) {
            contacts.remove(number);
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("No se encontró un contacto con ese número telefónico.");
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.csv");
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            System.out.println("-------- Menú --------");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    addressBook.create();
                    break;
                case 3:
                    addressBook.delete();
                    break;
                case 4:
                    addressBook.save();
                    System.out.println("Saliendo...");
                    break;
                default:
                System.out.println("Opción inválida. Intente de nuevo.");
                break;
            }
        }
    }
}

