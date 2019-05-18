package com.vorozhbicky.dmitry.calculationoflineardcelectriccircuits;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static java.lang.Math.cos;
import static java.lang.Math.cosh;
import static java.lang.Math.sin;

public class CalculationActivity extends AppCompatActivity {
    private TextView calculationTextView;
    private static final double fUA = 0;
    private static final double fUB = -120;
    private static final double fUC = 120;
    private static final int exponent = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        setTitle("Calculation");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        calculationTextView = findViewById(R.id.calculationTextView);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        System.out.println(type);

        if (type.equals("star")) {
            starCalculation(intent);
        } else {
            triangleCalculation(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("SetTextI18n")
    private void starCalculation(Intent intent) {
        //Присваиваем значения исходным данным и типы переменных
        double RA = Double.parseDouble(intent.getStringExtra("ra"));
        double RB = Double.parseDouble(intent.getStringExtra("rb"));
        double RC = Double.parseDouble(intent.getStringExtra("rc"));
        double XCA = Double.parseDouble(intent.getStringExtra("xca"));
        double XCB = Double.parseDouble(intent.getStringExtra("xcb"));
        double XCC = Double.parseDouble(intent.getStringExtra("xcc"));
        double XLA = Double.parseDouble(intent.getStringExtra("xla"));
        double XLB = Double.parseDouble(intent.getStringExtra("xlb"));
        double XLC = Double.parseDouble(intent.getStringExtra("xlc"));
        double UL = Double.parseDouble(intent.getStringExtra("ul"));
        //1.Определяем Фазное напряжение
        double Uf = UL / Math.sqrt(0x3);
        //переводим напряжение в алгебраическую форму
        double UfAAA=Uf*cos(Math.toRadians(fUA));
        double UfABA=Uf*cos(Math.toRadians(fUB));
        double UfACA=Uf*cos(Math.toRadians(fUC));

        double UfAAR=Uf*sin(Math.toRadians(fUA));
        double UfABR=Uf*sin(Math.toRadians(fUB));
        double UfACR=Uf*sin(Math.toRadians(fUC));


        //3.Выражение сопротивлений фаз в комплексной форме
        //Активные составляющие сопротивления
        //Реактивные составляющие сопротивления
        double ZkAR = XLA - XCA;
        double ZkBR = XLB - XCB;
        double ZkCR = XLC - XCC;
        //Перевод комплексных сопротивлениф фаз из алгебраической формы в показательную
        //Вычисляем и присваиваем значения угла сдвига фаз переменным φZA φZB φZC
        double fZA = Math.atan(ZkAR / RA);
        double fZB = Math.atan(ZkBR / RB);
        double fZC = Math.atan(ZkCR / RC);

        //Вычисляем и присваиваем значения полного сопротивления переменным ZKA ZKB ZKC
        double ZKA = Math.sqrt(Math.pow(RA, exponent) + Math.pow(ZkAR, exponent));
        double ZKB = Math.sqrt(Math.pow(RB, exponent) + Math.pow(ZkBR, exponent));
        double ZKC = Math.sqrt(Math.pow(RC, exponent) + Math.pow(ZkCR, exponent));
        //4.Находим комплексы фазных токов
        //Вычисляем и присваиваем значения тока переменным IKA IKB IKC
        double IKA = Uf / ZKA;
        double IKB = Uf / ZKB;
        double IKC = Uf / ZKC;
        //Вычисляем и присваиваем значения угла сдвига фаз тока переменным φIKA φIKB φIKC
        double fIKA = fUA - Math.toDegrees(fZA);
        double fIKB = fUB - Math.toDegrees(fZB);
        double fIKC = fUC - Math.toDegrees(fZC);

        //Вычисляем и присваиваем значения переменных IalgAA IalgBA IalgCA
        double IalgAA = IKA * cos(Math.toRadians(fIKA));
        double IalgBA = IKB * cos(Math.toRadians(fIKB));
        double IalgCA = IKC * cos(Math.toRadians(fIKC));
        //Вычисляем и присваиваем значения переменных IalgAR IalgBR IalgCR
        double IalgAR = IKA * sin(Math.toRadians(fIKA));
        double IalgBR = IKB * sin(Math.toRadians(fIKB));
        double IalgCR = IKC * sin(Math.toRadians(fIKC));
        //5.Вычисляем ток в нейтральном проводе
        //Вычисляем и присваиваем значчения активной и реактивной составляющей ток в нейтральном проводе переменным INA INR
        double INA = IalgAA + IalgBA + IalgCA;
        double INR = IalgAR + IalgBR + IalgCR;
        //Вычисляем и присваиваем значения тока в нейтральном проводе IN
        double IN = Math.sqrt(Math.pow(INA, exponent) + Math.pow(INR, exponent));
        //Вычисляем и присваиваем переменной MIN угол сдвига фазы тока в нейтральном проводе
        double MIN = Math.atan(INR / INA);

        //Масштабирование!!!!!!!!!!!!!!!!!!!

        double max=Math.max(Math.abs(IalgAA),Math.max(Math.abs(IalgBA),Math.abs(IalgCA)));
        double min=Math.min(Math.abs(IalgAA),Math.min(Math.abs(IalgBA),Math.abs(IalgAA)));
        double k=max/min;

        //Новые корды
        double mUfAAA=500+UfAAA;
        double mUfABA=500+UfABA;
        double mUfACA=500+UfACA;

        double mUfAAR=500-UfAAR;
        double mUfABR=500-UfABR;
        double mUfACR=500-UfACR;

        double mIalgAA=500+k*IalgAA;
        double mIalgBA=500+k*IalgBA;
        double mIalgCA=500+k*IalgCA;

        double mIalgAR=500-k*IalgAR;
        double mIalgBR=500-k*IalgBR;
        double mIalgCR=500-k*IalgCR;

        double mINA=500+k*INA;
        double mINR=500-k*INR;

        //Вычисляем мощности фаз и всей цепи
        //Возьмем знак угла сдвига тока фазы обратный исходному т.е будем домножать на -1
        //Вычисляем и присваиваем значения отрицательное значение угла сдвига фаз тока переменным MφIKA MφIKB MφIKC
        double MfIKA = -1 * fIKA;
        double MfIKB = -1 * fIKB;
        double MfIKC = -1 * fIKC;
        //Вычисляем и присваиваем значения суммы углов сдвига фаз тока и напряжения пременнным SφA SφB SφC
        double SfA = fUA + MfIKA;
        double SfB = fUB + MfIKB;
        double SfC = fUC + MfIKC;
        //Вычисляем и присваиваем значения активной составляющей мощности переменным SFAA SFBA SFCA
        double SFAA = Uf * IKA * cos(Math.toRadians(SfA));
        double SFBA = Uf * IKB * cos(Math.toRadians(SfB));
        double SFCA = Uf * IKC * cos(Math.toRadians(SfC));
        //Вычисляем и присваиваем значения реактивной составляющей мощности переменным SFAR SFBR SFCR
        double SFAR = Uf * IKA * sin(Math.toRadians(SfA));
        double SFBR = Uf * IKB * sin(Math.toRadians(SfB));
        double SFCR = Uf * IKC * sin(Math.toRadians(SfC));
        //Вычисляем и присваиваем значения произведения тока и напряжения переменным PUIA PUIB PUIC
        double PUIA = Uf * IKA;
        double PUIB = Uf * IKB;
        double PUIC = Uf * IKC;
        //Вычисляем и присваиваем активную и реактивную составляющие значению полной мощности переменным SPOLNA SPLONR
        double SPOLNA = SFAA + SFBA + SFCA;
        double SPOLNR = SFAR + SFBR + SFCR;
        //Вычисляем и присваиваем значение полной мощности переменной SPOLN
        double SPOLN = Math.sqrt(Math.pow(SPOLNA, exponent) + Math.pow(SPOLNR, exponent));
        //Вычисляем и присваиваем угол сдвига фазы мощности переменной φSPOLN
        double fSPOLN = Math.atan(SPOLNR / SPOLNA);

        //Вывод результатов на экран
        calculationTextView.setText("Расчет 'Звезда':\n\n" +
                "1.Определяем фазное напряжение:\n" + "Uф=" +Math.ceil(Uf) + "B\n\n" +
                "2.Выразим в комплексной форме напряжения:\n" +
                "UfA=" + Math.ceil(Uf) + "*e^j* " + fUA + "°\n" +
                "UfB=" + Math.ceil(Uf) + "*e^j* " + fUB + "°\n" +
                "UfC=" + Math.ceil(Uf) + "*e^j* " + fUC + "°\n\n" +

                "3.Выразим в комплексной форме сопротивления фаз:\n" +
                "ZкA=RA+jXLA-jXCA=(" + RA + "+j*" + ZkAR + ")Ом\n" +
                "ZкB=RB+jXLB-jXCB=(" + RB + "+j*" + ZkBR + ")Ом\n" +
                "ZкC=RC+jXLC-jXCC=(" + RC + "+j*" + ZkCR + ")Ом\n\n" +

                "Переведем комплексные сопротивления фаз из алгебраической в показатульную:\n" +
                "ZкA=" + RA + "+j*" + ZkAR + "=√("+ RA +")^2 +("+ZkAR+")^2 *e^j*arctg("+ZkAR+"/"+RA+")="+String.format("%.2f",ZKA)+"*e^j*"+String.format("%.2f",Math.toDegrees(fZA))+"° Ом\n"
                +"ZA=" + String.format("%.2f", ZKA) + "Ом - полное сопротивление фазы А, φA=" + String.format("%.2f", Math.toDegrees(fZA)) + "° - Угол сдвига фаз между током и напряжением в фазе А\n\n" +

                "ZкB=" + RB + "+j*" + ZkBR + "=√(" + RB +")^2 +("+ZkBR+"^2 *e^j *arctg("+ZkBR + "/" + RB + ")="+ String.format("%.2f", ZKB) + "*e^j*" + String.format("%.2f", Math.toDegrees(fZB)) + "° Ом\n" +
                "ZB=" + String.format("%.2f", ZKB) + "Ом - полное сопротивление фазы B, φB=" + String.format("%.2f", Math.toDegrees(fZB)) + "° - Угол сдвига фаз между током и напряжением в фазе B\n\n" +

                "ZкС=" + RC + "+j*" + ZkCR + "=√(" + RC + ")^2 +("+ZkCR + "^2 *e^j *arctg(" + ZkCR + "/" + RC + ")="+ String.format("%.2f", ZKC) + "*e^j*" + String.format("%.2f", Math.toDegrees(fZC)) + "° Ом\n" +
                "ZC=" +String.format("%.2f", ZKC) + "Ом - полное сопротивление фазы C, φC=" + String.format("%.2f", Math.toDegrees(fZC)) + "° - Угол сдвига фаз между током и напряжением в фазе C\n\n" +

                "4.Находим комплексы фазных токов:\n" +
                "IфА=UфА/ZкА=("+Math.ceil(Uf) + "*e^j*" + fUA + "°)/(" +String.format("%.2f",ZKA) + "*e^j*" + String.format("%.2f",Math.toDegrees(fZA)) + "°)=" + String.format("%.2f",IKA) + "*e^j* "+String.format("%.2f",fIKA)+"° А\n" +
                "Модуль IA=" + String.format("%.2f",IKA) + " А, аргумент ψА=" + String.format("%.2f",fIKA) + "°\n\n" +

                "IфB=UфB/ZкB(="+Math.ceil(Uf) + "*e^j*" + fUB + "°)/(" +String.format("%.2f",ZKB) + "*e^j*" + String.format("%.2f",Math.toDegrees(fZB)) + "°)=" + String.format("%.2f",IKB) + "*e^j*" +String.format("%.2f",fIKB) + "°А\n" +
                "Модуль IB=" + String.format("%.2f",IKB) + " А, аргумент ψB=" + String.format("%.2f",fIKB) + "°\n\n" +

                "IфC=UфC/ZкC(="+Math.ceil(Uf) + "*e^j*" + fUC + "°)/(" +String.format("%.2f",ZKC) + "*e^j*" + String.format("%.2f",Math.toDegrees(fZC)) + "°)=" + String.format("%.2f",IKC) + "*e^j*" +String.format("%.2f",fIKC) + "°А\n" +
                "Модуль IC=" + String.format("%.2f",IKC) + " А, аргумент ψC=" + String.format("%.2f",fIKC) + "°\n\n" +

                "Находим алгебраическую форму записи комплексов фазных токов:\n" +
                "IфА=" + String.format("%.2f",IKA) + "*e^j*" + String.format("%.2f",fIKA) + "° =" + String.format("%.2f",IKA) + "*cos(" + String.format("%.2f",fIKA) + "°)+" + String.format("%.2f",IKA) + "*sin(" + String.format("%.2f",fIKA) + "°)=(" + String.format("%.2f",IalgAA) + "+j*" + String.format("%.2f",IalgAR) + ")А\n" +
                "IфB=" + String.format("%.2f",IKB) + "*e^j*" + String.format("%.2f",fIKB) + "° =" + String.format("%.2f",IKB) + "*cos(" + String.format("%.2f",fIKB) + "°)+" + String.format("%.2f",IKB) + "*sin(" + String.format("%.2f",fIKB) + "°)=(" + String.format("%.2f",IalgBA) + "+j*" + String.format("%.2f",IalgBR) + ")А\n" +
                "IфC=" + String.format("%.2f",IKC) + "*e^j*" + String.format("%.2f",fIKC) + "° =" + String.format("%.2f",IKC) + "*cos(" + String.format("%.2f",fIKC) + "°)+" + String.format("%.2f",IKC) + "*sin(" + String.format("%.2f",fIKC) + "°)=(" + String.format("%.2f",IalgCA) + "+j*" + String.format("%.2f",IalgCR) + ")А\n\n" +

                "5.Вычисляем ток в нейтральном проводе:\n" +
                "Iн= IфА+ IфB+ IфC=" + String.format("%.2f",IalgAA) + "+j*" + String.format("%.2f",IalgAR) + "+" + String.format("%.2f",IalgBA) + "+j*" + String.format("%.2f",IalgBR) + "+" + String.format("%.2f",IalgCA) + "+j*" + String.format("%.2f",IalgCR) + "=" + String.format("%.2f",INA) + "+j*" + String.format("%.2f",INR) + "=" + String.format("%.2f",IN) + "*e^j*" + String.format("%.2f",Math.toDegrees(MIN)) + "° A\n" +
                "Модуль Iн=" + String.format("%.2f",IN) + "A, аргумент ψN=" + String.format("%.2f",Math.toDegrees(MIN)) + "°\n\n" +


                "6.Вычисляем мощности фаз и всей цепи:\n" +
                "SфА=UфА*Iфа=" + String.format("%.2f",Uf) + "*e^j* " + fUA + "° *" + String.format("%.2f",IKA) + "*e^j*" + String.format("%.2f",MfIKA) + "°=" + String.format("%.2f",PUIA) + "*cos(" + String.format("%.2f",SfA) + "°)+j" + String.format("%.2f",PUIA) + "*sin(" + String.format("%.2f",SfA) +"°) = (" + String.format("%.2f",SFAA) + "+j*" + String.format("%.2f",SFAR) + ") В*А\n" +
                "где SA=" + String.format("%.2f",PUIA) + " B*A, PA=" + String.format("%.2f",SFAA) + " Вт, QA=" + String.format("%.2f",SFAR) + "вар\n" +

                "SфB=UфB*Iфb=" + String.format("%.2f",Uf) + "e^j* " + fUB + "°*" + String.format("%.2f",IKB) + "*e^j*" + String.format("%.2f",MfIKB) + "°=" + String.format("%.2f",PUIB) + "*cos(" + String.format("%.2f",SfB) + "°)+j" + String.format("%.2f",PUIB) + "*sin(" + String.format("%.2f",SfB) + "°) = (" + String.format("%.2f",SFBA) + "+j*" + String.format("%.2f",SFBR) + ") В*А\n" +
                "где SB=" + String.format("%.2f",PUIB) + "B*A, PB=" + String.format("%.2f",SFBA) + "Вт, QB=" + String.format("%.2f",SFBR) + "вар\n" +

                "SфC=UфC*Iфc=" + String.format("%.2f",Uf) + "e^j* " + fUC + "°*" + String.format("%.2f",IKC) + "*e^j*" + String.format("%.2f",MfIKC) + "°=" + String.format("%.2f",PUIC) + "*cos(" + String.format("%.2f",SfC) + "°)+j" + String.format("%.2f",PUIC) + "*sin(" + String.format("%.2f",SfC) + "°) = (" + String.format("%.2f",SFCA) + "+j*" + String.format("%.2f",SFCR) + ") *А\n" +
                "где SC=" + String.format("%.2f",PUIC) + "B*A, PC=" + String.format("%.2f",SFCA) + "Вт, QC=" + String.format("%.2f",SFCR) + "вар\n" +

                "Sполн = SфА+ SфB+ SфC=" + String.format("%.2f",SFAA) + "+" + String.format("%.2f",SFBA) + "+" + String.format("%.2f",SFCA) + "+j*" + String.format("%.2f",SFAR) + "+j*" + String.format("%.2f",SFBR) + "+j*" + String.format("%.2f",SFCR) + "=" + String.format("%.2f",SPOLNA) + "+j*" + String.format("%.2f",SPOLNR) + "=(" + String.format("%.2f",SPOLN) + "*e^j" + String.format("%.2f",fSPOLN) + "°) В*А\n" +
                "где S=" + String.format("%.2f",SPOLN) + "В*А, P=" + String.format("%.2f",SPOLNA) + "Вт, Q=" + String.format("%.2f",SPOLNR) + "Вар");



    }




    @SuppressLint("SetTextI18n")
    private void triangleCalculation(Intent intent) {
//Присваиваем значения исходным данны и типы перменных
        double rab = Double.parseDouble(intent.getStringExtra("rab"));
        double rbc = Double.parseDouble(intent.getStringExtra("rbc"));
        double rac = Double.parseDouble(intent.getStringExtra("rac"));
        double xlab = Double.parseDouble(intent.getStringExtra("xlab"));
        double xlbc = Double.parseDouble(intent.getStringExtra("xlbc"));
        double xlac = Double.parseDouble(intent.getStringExtra("xlac"));
        double xcab = Double.parseDouble(intent.getStringExtra("xcab"));
        double xcbc = Double.parseDouble(intent.getStringExtra("xcbc"));
        double xcac = Double.parseDouble(intent.getStringExtra("xcac"));
        double ul = Double.parseDouble(intent.getStringExtra("ul"));
//переводим напряжение в алгебраическую форму
        double UfAABA=ul*cos(Math.toRadians(fUA));
        double UfABCA=ul*cos(Math.toRadians(fUB));
        double UfAACA=ul*cos(Math.toRadians(fUC));

        double UfAABR=ul*sin(Math.toRadians(fUA));
        double UfABCR=ul*sin(Math.toRadians(fUB));
        double UfAACR=ul*sin(Math.toRadians(fUC));

//6.Вычислим и присвоим значения комплексных фазных реактивных сопротивлений переменным ZABR1 ZBCR1 ZACR1
        double ZABR1= xlab-xcab;
        double ZBCR1= xlbc-xcbc;
        double ZACR1= xlac-xcac;
//7.Вычислим и присвоим значения полного сопротивления и угла сдвига фаз переменным
        double ZAB1=Math.sqrt(Math.pow(rab,exponent)+Math.pow(ZABR1,exponent));
        double ZBC1=Math.sqrt(Math.pow(rbc,exponent)+Math.pow(ZBCR1,exponent));
        double ZAC1=Math.sqrt(Math.pow(rac,exponent)+Math.pow(ZACR1,exponent));

        double fZAB1=Math.atan(ZABR1/ rab);
        double fZBC1=Math.atan(ZBCR1/ rbc);
        double fZAC1=Math.atan(ZACR1/ rac);
//8. Вычисляем и присваиваем значения переменным тока и углу сдвига фаз тока IAB1 IBC1 IAC1, φIAB1 φIBC1 φIAC1
        double IAB1= ul/ZAB1;
        double IBC1= ul/ZBC1;
        double IAC1= ul/ZAC1;

        double fIAB1= fUA-Math.toDegrees(fZAB1);
        double fIBC1= fUB-Math.toDegrees(fZBC1);
        double fIAC1= fUC-Math.toDegrees(fZAC1);
//9.Вычисляем и присваиваме знаничения переменным активной и реактивной составляющей тока IABA1 IBCA1 IACA1 IABR1 IBCR1 IACR1
        double IABA1= IAB1*Math.cos (Math.toRadians(fIAB1));
        double IBCA1= IBC1*Math.cos (Math.toRadians(fIBC1));
        double IACA1= IAC1*Math.cos (Math.toRadians(fIAC1));

        double IABR1=IAB1*Math.sin(Math.toRadians(fIAB1));
        double IBCR1=IBC1*Math.sin(Math.toRadians(fIBC1));
        double IACR1=IAC1*Math.sin(Math.toRadians(fIAC1));
//10.Вычисляем и присваиваем активные и реактивные составляющие линейных токов переменным IA1A IB1A IC1A IA1R IB1R IC1R
        double IA1A= IABA1- IACA1;
        double IB1A= IBCA1- IABA1;
        double IC1A= IACA1- IBCA1;

        double IA1R= IABR1- IACR1;
        double IB1R= IBCR1- IABR1;
        double IC1R= IACR1- IBCR1;
//11. Вычисляем и присваиваем значения линейным токам IA1 IB1 IC1
        double IA1=Math.sqrt(Math.pow(IA1A,exponent)+Math.pow(IA1R,exponent));
        double IB1=Math.sqrt(Math.pow(IB1A,exponent)+Math.pow(IB1R,exponent));
        double IC1=Math.sqrt(Math.pow(IC1A,exponent)+Math.pow(IC1R,exponent));
//12.Вычисляем углы сдвига фаз линейных токов φIA1 φIB1 φIC1
        double fIA1= Math.atan(IA1R/ IA1A);
        double fIB1= Math.atan(IB1R/ IB1A);
        double fIC1= Math.atan(IC1R/ IC1A);
//Масштабирование!!!!!!!!!!!!!!!!11
        double max=Math.max(Math.abs(IABA1),Math.max(Math.abs(IBCA1),Math.abs(IACA1)));
        double min=Math.min(Math.abs(IABA1),Math.min(Math.abs(IBCA1),Math.abs(IACA1)));
        double k=max/min;

        double mUfAABA=500+UfAABA;
        double mUfABCA=500+UfABCA;
        double mUfAACA=500+UfAACA;

        double mUfAABR=500-UfAABR;
        double mUfABCR=500-UfABCR;
        double mUfAACR=500-UfAACR;

        double mIABA1=500+k*IABA1;
        double mIBCA1=500+k*IBCA1;
        double mIACA1=500+k*IACA1;

        double mIABR1=500-k*IABR1;
        double mIBCR1=500-k*IBCR1;
        double mIACR1=500-k*IACR1;

        double mIA1A=500+k*IA1A;
        double mIB1A=500+k*IB1A;
        double mIC1A=500+k*IC1A;

        double mIA1R=500-k*IA1R;
        double mIB1R=500-k*IB1R;
        double mIC1R=500-k*IC1R;


//13.Вычисляем и присваиваем обратные углы сдвига фаз тока переменным φOIAB1 φOIBC1 φOIAC1
        double fOIAB1=-1*fIAB1;
        double fOIBC1=-1*fIBC1;
        double fOIAC1=-1*fIAC1;
//14.Вычисляем и присваиваем значения мощности SAB1 SBC1 SAC1
        double SAB1= IAB1* ul;
        double SBC1= IBC1* ul;
        double SAC1= IAC1* ul;
//15. Вычисляем и присваиваем значения угла сдвига фаз φSAB1 φSBC1φSAC1
        double fSAB1= fUA+ fOIAB1;
        double fSBC1= fUB+ fOIBC1;
        double fSAC1= fUC+ fOIAC1;
//16.Вычисляем и присваиваем значения активной и реактивной составляющей мощности переменным SABA1 SBCA1 SACA1 SABR1 SBCR1 SACR1
        double SABA1=SAB1*Math.cos(Math.toRadians(fSAB1));
        double SBCA1=SBC1*Math.cos(Math.toRadians(fSBC1));
        double SACA1=SAC1*Math.cos(Math.toRadians(fSAC1));

        double SABR1=SAB1*Math.sin(Math.toRadians(fSAB1));
        double SBCR1= SBC1*Math.sin(Math.toRadians(fSBC1));
        double SACR1= SAC1*Math.sin(Math.toRadians(fSAC1));
        //Вычисляем и присваиваем активную и реактивную составляющие значению полной мощности
        double SPOLNA1 = SABA1 + SBCA1 + SACA1;
        double SPOLNR1 = SABR1 + SBCR1 + SACR1;
        //Вычисляем и присваиваем значение полной мощности переменной SPOLN
        double SPOLN1 = Math.sqrt(Math.pow(SPOLNA1, exponent) + Math.pow(SPOLNR1, exponent));
        //Вычисляем и присваиваем угол сдвига фазы мощности переменной φSPOLN
        double fSPOLN1 = Math.atan(SPOLNR1 / SPOLNA1);

        //Вывод результатов на экран
        calculationTextView.setText("Расчет 'Треугольник':\n\n" +
                "1.Модули фазных напряжений при соединении треугольником равны линейным напряжениям\n"
                + "Uл=Uф="+ul+"В Т.е UAB=UAC=UCA="+ul+"В\n"
                + "Комплексы данных напряжений:\n" +
                "UAB="+ul+"*e^j* "+fUA+"° B\n" + "UBC="+ul+"*e^j* "+fUB+"° B\n" + "UAC="+ul+"*e^j* "+fUC+"° B\n\n" +

                "2.Вычислим комплексы фазных сопротивлений:\n"+
                "ZAB=RAB+j*XLAB-j*XCAB="+rab+"+j*"+ZABR1+"="+ String.format("%.2f",ZAB1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZAB1))+"° Ом\n" +
                "где ZAB="+String.format("%.2f",ZAB1)+"Ом, φAB="+String.format("%.2f",Math.toDegrees(fZAB1))+"°\n" +

                "ZBC=RBC+j*XLBC-j*XCBC="+rbc+"+j*"+ZBCR1+"="+String.format("%.2f",ZBC1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZBC1))+"° Ом\n" +
                "где ZBC="+String.format("%.2f",ZBC1)+"Ом, φBC="+String.format("%.2f",Math.toDegrees(fZBC1))+"°\n" +

                "ZAC=RAC+j*XLAC-j*XCAC="+rac+"+j*"+ZACR1+"="+String.format("%.2f",ZAC1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZAC1))+"° Ом\n" +
                "где ZAC="+String.format("%.2f",ZAC1)+"Ом ,φAC="+String.format("%.2f",Math.toDegrees(fZAC1))+"°\n\n" +

                "3.Вычисляем фазные токи:\n" +
                "IAB= UAB/ZAB=("+ul+"*e^j*"+(fUA)+"°)/("+String.format("%.2f",ZAB1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZAB1))+"°) = "+String.format("%.2f",IAB1)+ "*e^j* "+String.format("%.2f",fIAB1)+"°="+String.format("%.2f",IABA1)+"+j*"+String.format("%.2f",IABR1)+"A\n" +
                "Модуль IAB="+String.format("%.2f",IAB1)+ "A, ψАB="+String.format("%.2f",fIAB1)+ "°;\n"+
                "IBC= UBC/ZBC=("+ul+"*e^j*"+(fUB)+"°)/("+String.format("%.2f",ZBC1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZBC1))+"° )= "+String.format("%.2f",IBC1)+ "*e^j* "+String.format("%.2f",fIBC1)+"°="+String.format("%.2f",IBCA1)+"+j*"+String.format("%.2f",IBCR1)+"A\n" +
                "Модуль IBC="+String.format("%.2f",IBC1)+ "A, ψАB="+String.format("%.2f",fIBC1)+ "°;\n"+
                "IAC= UAC/ZAC=("+ul+"*e^j*"+(fUC)+"°)/("+String.format("%.2f",ZAC1)+"*e^j* "+String.format("%.2f",Math.toDegrees(fZAC1))+"°) = "+String.format("%.2f",IAC1)+ "*e^j* "+String.format("%.2f",fIAC1)+"°="+String.format("%.2f",IACA1)+"+j*"+String.format("%.2f",IACR1)+"A\n" +
                "Модуль IAC="+String.format("%.2f",IAC1)+ "A, ψАB="+String.format("%.2f",fIAC1)+ "°;\n\n"+


                "4.Находим линейные токи из уравнений записанных по первому закону Кирхгофа для узлов A,B,C\n" +
                "IA=IAB-ICA="+String.format("%.2f",IA1A)+"-j*"+String.format("%.2f",IA1)+"="+String.format("%.2f",IA1)+"*e^j* "+String.format("%.2f",(Math.toDegrees(fIA1)))+"°A\n" +
                "Модуль IA="+String.format("%.2f",IA1)+"А, аргумент ψА="+String.format("%.2f",(Math.toDegrees(fIA1)))+"° \n\n" +

                "IB=IBC-IAB="+String.format("%.2f",IB1A)+"-j*"+String.format("%.2f",IB1R)+"="+String.format("%.2f",IB1)+"*e^j* "+String.format("%.2f",(Math.toDegrees(fIB1)))+"°A\n" +
                "Модуль IB="+String.format("%.2f",IB1)+"А, аргумент ψB="+String.format("%.2f",(Math.toDegrees(fIB1)))+"° \n\n" +

                "Ic=IAC-IBC="+String.format("%.2f",IC1A)+"-j*"+String.format("%.2f",IC1R)+"="+String.format("%.2f",IC1)+"*e^j* "+String.format("%.2f",(Math.toDegrees(fIC1)))+"°A\n" +
                "Модуль IC="+String.format("%.2f",IC1A)+"А, аргумент ψC="+String.format("%.2f",(Math.toDegrees(fIC1)))+"° \n\n" +

                "5.Вычисляем мощности каждой фазы и всей цепи:\n" +
                "SAB=UAB*IAB="+ul+"*e^j"+fUA+"° *"+String.format("%.2f",IAB1)+"*e^j *"+String.format("%.2f",fOIAB1)+"° ="+String.format("%.2f",SAB1)+"*e^j* "+String.format("%.2f",fSAB1)+"° = ("+String.format("%.2f",SABA1)+"+j*"+String.format("%.2f",SABR1)+")B*A\n" +
                "где SAB="+String.format("%.2f",SAB1)+"B*A, PAB="+String.format("%.2f",SABA1)+"Вт QAB="+String.format("%.2f",SABR1)+"вар\n" +
                "SBC=UBC*IBC="+ul+"*e^j"+fUB+"° *"+String.format("%.2f",IBC1)+"*e^j* "+String.format("%.2f",fOIBC1)+"°="+String.format("%.2f",SBC1)+"*e^j* "+String.format("%.2f",fSBC1)+"° = ("+String.format("%.2f",SBCA1)+"+j*"+String.format("%.2f",SBCR1)+")B*A\n" +
                "где SBC="+String.format("%.2f",SBC1)+"B*A PBC="+String.format("%.2f",SBCA1)+"Вт, QBC="+String.format("%.2f",SBCR1)+"вар\n" +
                "SBC=UAC*IAC="+ul+"*e^j"+fUC+"° *"+String.format("%.2f",IAC1)+"*e^j* "+String.format("%.2f",fOIAC1)+"° ="+String.format("%.2f",SAC1)+"*e^j* "+String.format("%.2f",fSAC1)+"° = ("+String.format("%.2f",SACA1)+"+j*"+String.format("%.2f",SACR1)+"B*A\n" +
                "где SAC="+String.format("%.2f",SAC1)+"B*A PAC="+String.format("%.2f",SACA1)+"Вт, QAC="+String.format("%.2f",SACR1)+"вар\n"+
                "Sполн = SфАB+ SфBC+ SфAC=" + String.format("%.2f",SABA1) + "+" + String.format("%.2f",SBCA1) + "+" + String.format("%.2f",SACA1) + "+j*" + String.format("%.2f",SABR1) + "+j*" + String.format("%.2f",SBCR1) + "+j*" + String.format("%.2f",SACR1) + "=" + String.format("%.2f",SPOLNA1) + "+j*" + String.format("%.2f",SPOLNR1) + "=(" + String.format("%.2f",SPOLN1) + "*e^j" + String.format("%.2f",fSPOLN1) + "°) В*А\n" +
                "где S=" + String.format("%.2f",SPOLN1) + "В*А, P=" + String.format("%.2f",SPOLNA1) + "Вт, Q=" + String.format("%.2f",SPOLNR1) + "Вар");;





    }

    public void nextToDiagram(View view) {
        Intent intent = new Intent(CalculationActivity.this, DiagramActivity.class);
        startActivity(intent);
    }
}


