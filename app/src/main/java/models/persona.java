package models;

import android.net.Uri;

import androidx.annotation.NonNull;

public class persona {
    private String nombre;
    private String apellidos;
    private String sexo;
    private String ciudad;
    private int edad;
    private String DNI;
    private double peso;
    private double altura;
    private byte[] foto;

    public persona(String nombre, String apellidos, String sexo, String ciudad, int edad, String DNI, double peso, double altura, byte[] foto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.DNI = DNI;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    //get de item Targeta
    public byte[] getFoto() {
        return foto;
    }
    public String getNombreCompleto() {
        return apellidos + ", " + nombre;
    }
    public String getTipoPeso() {
        String[] tipopeso = {"Flaco", "perfecto", "Gordo"};
        return tipopeso[calcularIMC() + 1];
    }
    public String getTipoPersona(){
        return esMayorDeEdad() ? "Es Mayor de Edad" : "No Es Mayor de Edad";
    }
    public String getSexo(){
        return sexo;
    }
    public String getCiudad(){
        return "Ciudad procedencia: "+ ciudad;
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
        return  "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Sexo:" + sexo + '\n' +
                "Ciudad:" + ciudad + '\n' +
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
