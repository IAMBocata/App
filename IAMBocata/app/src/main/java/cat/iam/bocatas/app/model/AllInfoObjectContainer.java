package cat.iam.bocatas.app.model;

/**
 * Classe estatica que conté l'objecte amb tota la informació que necessita l'aplicació.
 */


public class AllInfoObjectContainer {

    private static AllInfoObject allInfoObject;

    public static AllInfoObject getAllInfoObject() {
        return allInfoObject;
    }

    public static void setAllInfoObject(AllInfoObject allInfoObject) {
        AllInfoObjectContainer.allInfoObject = allInfoObject;
    }
}
