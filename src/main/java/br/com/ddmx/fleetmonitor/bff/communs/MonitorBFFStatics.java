package br.com.ddmx.fleetmonitor.bff.communs;

public class MonitorBFFStatics {
    public static enum ETL_INDEX {

        LIMETE_VELOCIDADE("limite_velocidade_controle");

        private final String nome;

        private ETL_INDEX(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }
    }
}
