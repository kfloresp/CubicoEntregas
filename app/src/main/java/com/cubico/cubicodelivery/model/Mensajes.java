package com.cubico.cubicodelivery.model;

public class Mensajes{
        private int  Cantidad;
        private int  ERROR;
        private String MENSAGE;
        private String Valor2;

        public int getCantidad() {
                return Cantidad;
        }

        public void setCantidad(int cantidad) {
                Cantidad = cantidad;
        }

        public int getERROR() {
                return ERROR;
        }

        public void setERROR(int ERROR) {
                this.ERROR = ERROR;
        }

        public String getMENSAGE() {
                return MENSAGE;
        }

        public void setMENSAGE(String MENSAGE) {
                this.MENSAGE = MENSAGE;
        }

        public String getValor2() {
                return Valor2;
        }

        public void setValor2(String valor2) {
                Valor2 = valor2;
        }
}
