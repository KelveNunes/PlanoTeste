package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

class Pagamento {
    public String Empresa;
    public String Lancamento;
    public int Pago;

    public Pagamento() {}
}

class ResumoEmpresa {
    public String empresa;
    public int pagos;
    public int total;

    public ResumoEmpresa(String empresa) {
        this.empresa = empresa;
        this.pagos = 0;
        this.total = 0;
    }
}

public class Ranking {

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("C:\\Users\\kelve\\OneDrive\\Documentos\\Java\\Projetos\\PlanoTeste2\\src\\main\\java\\org\\example\\dados.json");

            List<Pagamento> pagamentos = mapper.readValue(file, new TypeReference<List<Pagamento>>() {});

            List<ResumoEmpresa> resumos = new ArrayList<>();
            for (Pagamento p : pagamentos) {
                ResumoEmpresa resumo = null;
                for (ResumoEmpresa r : resumos) {
                    if (r.empresa.equals(p.Empresa)) {
                        resumo = r;
                        break;
                    }
                }
                if (resumo == null) {
                    resumo = new ResumoEmpresa(p.Empresa);
                    resumos.add(resumo);
                }
                resumo.total++;
                if (p.Pago == 1) {
                    resumo.pagos++;
                }
            }

            resumos.sort(new Comparator<ResumoEmpresa>() {
                public int compare(ResumoEmpresa a, ResumoEmpresa b) {
                    double mediaA = (double) a.pagos / a.total;
                    double mediaB = (double) b.pagos / b.total;
                    return Double.compare(mediaB, mediaA);
                }
            });

            System.out.println("Ranking dos bons pagadores:");
            int posicao = 1;
            for (ResumoEmpresa r : resumos) {
                double porcentagem = (double) r.pagos / r.total * 100;
                System.out.printf("%d. %s - %.0f%%%n", posicao++, r.empresa, porcentagem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}