import java.util.ArrayList;

public class CatalogoEventos {
    private ArrayList<Evento> eventos;

    public CatalogoEventos(){
        this.eventos = new ArrayList<>();
    }

    public void agregarEvento(Evento evento){
        eventos.add(evento);
    }

    //Esto tendria que retornar una copia para no romper el encapsulamiento
    public ArrayList<Evento> retornarEventos(){
        return new ArrayList<>(eventos);
    }

}
