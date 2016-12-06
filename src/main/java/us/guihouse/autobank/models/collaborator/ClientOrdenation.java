package us.guihouse.autobank.models.collaborator;

/**
 * Created by valmir.massoni on 02/12/2016.
 */
public class ClientOrdenation {

    public enum ClientOrder {
        NAME, EMAIL, CPF, BIRTHDAY
    }

    private ClientOrder ordenation;
    private Boolean direction; //if true ASC, if not DESC

    public ClientOrdenation(ClientOrder ordenation, Boolean direction) {
        this.ordenation = ordenation;
        this.direction = direction;
    }

    public void setOrdenation(ClientOrder ordenation) {
        this.ordenation = ordenation;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    public ClientOrder getOrdenation() {
        return ordenation;
    }

    public Boolean getDirection() {
        return direction;
    }
}
