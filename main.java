public class main {
    public static void main(String[] args){
        Autenticador.levantarDatos();
        System.out.println("Valido: "+Integer.toString(Autenticador.loginValido("76238734","JuancitoSuperPassword")));
        System.out.println("Valido: "+Integer.toString(Autenticador.loginValido("45567856897","JuancitoSuperPassword")));
        System.out.println("UserNoExisteOrg: "+Integer.toString(Autenticador.loginValido("45367856897","JuancitoSuperPassword")));
        System.out.println("UserNoExisteComp: "+Integer.toString(Autenticador.loginValido("11223344","JuancitoSuperPassword")));
        System.out.println("contraIncorrectComp: "+Integer.toString(Autenticador.loginValido("76238734","Juancassword")));
        System.out.println("contraIncorrectOrg: "+Integer.toString(Autenticador.loginValido("45567856897","Juanciword")));
        System.out.println("IdInvalido: "+Integer.toString(Autenticador.loginValido("6897","Juanciword")));
        Autenticador.guardarDatos();
    }
}
