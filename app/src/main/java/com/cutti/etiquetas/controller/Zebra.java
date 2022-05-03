package com.cutti.etiquetas.controller;


import android.widget.Switch;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;
import fr.w3blog.zpl.utils.ZebraUtils;

public class Zebra {


    public void imprimeEtiquetas(String manifesto, String codCliente, String razaoSocial, String qtdCaixas,
                                 String position, String tipo, String date, int ip) throws ZebraPrintException {
        ZebraLabel zebraLabel = new ZebraLabel(912, 912);
        zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

        zebraLabel.addElement(new ZebraNativeZpl("^XA\r\n"
                + "^MMT\r\n"
                + "^PW1248\r\n"
                + "^LL0886\r\n"
                + "^LS0\r\n"
                + "^FO29,25^GB367,84,84^FS\r\n"
                + "^FT29,92^A0N,67,67^FR^FH\\^FDMANIFESTO: ^FS\r\n"
                + "^FT32,191^A0N,67,67^FH\\^FDCliente: ^FS\r\n"
                + "^FO21,107^GB1134,0,9^FS\r\n"
                + "^FT259,498^A0N,150,148^FB45,1,0,C^FH\\^FD/^FS\r\n"
                + "^FT408,89^A0N,50,50^FH\\^FD " + manifesto + "^FS\r\n"
                + "^FT274,189^A0N,71,86^FH\\^FD " + codCliente + "^FS\r\n"
                + "^FT32,261^A0N,42,43^FH\\^FD " + razaoSocial + " ^FS\r\n"
                + "^FT14,496^A0N,200,199^FH\\^FD " + position + "^FS\r\n"
                + "^FT300,499^A0N,200,199^FH\\^FD" + qtdCaixas + "^FS\r\n"
                + "^FT534,464^A0N,83,81^FH\\^FD " + tipo + "^FS\r\n"
                + "^FT700,539^A0N,29,28^FH\\^FD" + date + "^FS\r\n"
                + "^PQ1,0,1,Y^XZ\r\n"));

        switch (ip){
            case 1:
                imprime("192.168.10.23", zebraLabel);
                break;
            case 2:
                imprime("192.168.10.21", zebraLabel);
                break;
            case 3:
                imprime("192.168.10.24", zebraLabel);
                break;
            case 4:
                imprime("192.168.10.22", zebraLabel);
        }


    }

    private void imprime(String ip, ZebraLabel zebraLabel) throws ZebraPrintException {


        ZebraUtils.printZpl(zebraLabel, ip, 9100);

        System.out.println("Imprimiu!");
    }
}

