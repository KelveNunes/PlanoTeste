package org.example.domain;

public class ResumoEmpresa {
    public String empresa;
    public int pagos;
    public int total;

    public ResumoEmpresa(String empresa) {
        this.empresa = empresa;
        this.pagos = 0;
        this.total = 0;
    }
}
