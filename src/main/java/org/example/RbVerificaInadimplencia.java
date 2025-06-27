package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Pagamento;
import org.example.domain.ResumoEmpresa;

import java.io.File;
import java.util.*;

public class RbVerificaInadimplencia {

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File dadosEmpresas = new File("src/main/resources/dados.json");

            List<Pagamento> pagamentos = mapper.readValue(dadosEmpresas, new TypeReference<>() {});

            List<ResumoEmpresa> resumos = new ArrayList<>();
            for (Pagamento p : pagamentos) {
                ResumoEmpresa resumo = null;
                for (ResumoEmpresa r : resumos) {
                    if (r.empresa.equals(p.nomeEmpresa)) {
                        resumo = r;
                        break;
                    }
                }
                if (resumo == null) {
                    resumo = new ResumoEmpresa(p.nomeEmpresa);
                    resumos.add(resumo);
                }
                resumo.total++;
                if (p.pago == 1) {
                    resumo.pagos++;
                }
            }

            resumos.sort(new Comparator<>() {
                public int compare(ResumoEmpresa a, ResumoEmpresa b) {
                    double mediaA = (double) a.pagos / a.total;
                    double mediaB = (double) b.pagos / b.total;
                    return Double.compare(mediaB, mediaA);
                }
            });

            System.out.println("RbVerificaInadimplencia dos bons pagadores:");
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