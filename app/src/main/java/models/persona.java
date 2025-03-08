package models;

import androidx.annotation.NonNull;

public class persona {
 private String nombre;
 private String apellidos;
 private int edad;
 private String DNI;
 private double peso;
 private double altura;

    public persona(String nombre, String apellidos, int edad, String DNI, Double peso, Double altura) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.DNI = DNI;
        this.peso = peso;
        this.altura = altura;
    }

    public int calcularIMC() {
        double imc = peso / (altura * altura);
        if (imc < 20) {
            return -1; // Bajo peso
        } else if (imc >= 20 && imc <= 25) {
            return 0; // Peso ideal
        } else {
            return 1; // Sobrepeso
        }
    }
    public boolean esMayorDeEdad() {
        return edad >= 18;
    }
    @NonNull
    @Override
    public String toString() {
        String[]tipopeso={"Flaco","perfecto","Gordo"};
        return "_______________________________" + "\n" +
                "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Edad: " + edad + "\n" +
                "DNI: " + DNI + "\n" +
                "Peso: " + peso + " kg\n" +
                "Altura: " + altura + " m\n" +
                "IMC: " + tipopeso[calcularIMC()+1] + "\n" +
                "Mayor de edad: " + (esMayorDeEdad() ? "Sí" : "No");
    }
    public boolean validarDNI() {
        if(DNI.length()==8)
            return true;
        else
            return false;
    }

}
