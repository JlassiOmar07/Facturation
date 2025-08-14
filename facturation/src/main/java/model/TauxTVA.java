package model;

public enum TauxTVA {
    ZERO(0), CINQ_CINQ(5.5), DIX(10), VINGT(20);

    private final double valeur;

    TauxTVA(double valeur) {
        this.valeur = valeur;
    }

    public double getValeur() {
        return valeur;
    }
}
