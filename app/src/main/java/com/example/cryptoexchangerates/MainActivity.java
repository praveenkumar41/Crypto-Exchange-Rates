package com.example.cryptoexchangerates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView inr,crypto;
    private AutoCompleteTextView select_coin;
    private Button show;
    private FloatingActionButton fab;
    private int name_pos=0,code_pos=0;
    private ImageButton flipusd,flipinr;
    private Double usd,inr_rupee,final_btc_price;
    private String codes[]={"RUNE","WAVES","KCS","BAT","CHZ","HT","CELO","HOT","DASH","YFI","TUSD","NEXO","COMP","IOTX","XEM","XDC","MINA","1INCH","TFUEL","DCR","USDP","CEL","QTUM","BORA","ROSE","REV","RVN","ICX","VGX","OMG","ETH","BNB","USDT","SOL","ADA","USDC","XRP","LUNA","AVAX","DOT","DOGE","SHIB","MATIC","BUSD","CRO","WBTC","LTC","DAI","UNI","UST","LINK","ALGO","BCH","TRX","XLM","MANA","AXS","FTT","HBAR","VET","NEAR","ATOM","BTCB","FIL","EGLD","SAND","ETC","ICP","THETA","HNT","XTZ","FTM","LEO","XMR","MIOTA","KLAY","GRT","EOS","BTC","GALA","CAKE","LRC","ONE","STX","BTT","FLOW","AAVE","MKR","BSV","KSM","QNT","ZEC","XEC","AMP","ENJ","OKB","CRV","NEO","AR","KDA","MNT","MOP","MRO","MRU","MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR","SDG","SEK","SGD","SHP","SLL","SOS","STD","THB","TJS","TMT","TND","TOP","TRY","TTD","TWD","TZS","UAH","UGX","USD","UYU","UZS","VES","VND","VUV","WST","XAF","XAG","XAU","XCD","XDR","XOF","XPD","XPF","XPT","YER","ZAR","ZMW","ZWL","AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD","AWG","AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD","BND","BOB","BRL","BSD","BTN","BWP","BYN","BZD","CAD","CDF","CHF","CLF","CLP","CNH","CNY","COP","CRC","KPW","KRW","KWD","KYD","KZT","LAK","SRD","SSP","STN","SVC","SYP","SZL","CUC","CUP","CVE","CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR","FJD","FKP","GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD","IRR","ISK","JEP","JMD","JOD","JPY","KES","KGS","KHR","KMF","LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA","MKD","MMK","VEF"};
    private String names[]={"THORChain","Waves","KuCoin Token","Basic Attention Token","Chiliz","Huobi Token","Celo","Holo","Dash","yearn.finance","TrueUSD","Nexo","Compound","IoTeX","NEM","XDC Network","Mina","1inch Network","Theta Fuel","Decred","Pax Dollar","Celsius","Qtum","BORA","Oasis Network","Revain","Ravencoin","ICON","Voyager Token","OMG Network","Ethereum","Binance Coin","Tether","Solana","Cardano","USD Coin","XRP","Terra","Avalanche","Polkadot","Dogecoin","SHIBA INU","Polygon","Binance USD","Crypto.com Coin","Wrapped Bitcoin","Litecoin","Dai","Uniswap","TerraUSD","Chainlink","Algorand","Bitcoin Cash","TRON","Stellar","Decentraland","Axie Infinity","FTX Token","Hedera","VeChain","NEAR Protocol","Cosmos","Bitcoin BEP2","Filecoin","Elrond","The Sandbox","Ethereum Classic","Internet Computer","THETA","Helium","Tezos","Fantom","UNUS SED LEO","Monero","IOTA","Klaytn","The Graph","EOS","Bitcoin","Gala","PancakeSwap","Loopring","Harmony","Stacks","BitTorrent","Flow","Aave","Maker","Bitcoin SV","Kusama","Quant","Zcash","eCash","Amp","Enjin Coin","OKB","Curve DAO Token","Neo","Arweave","Kadena","Mongolian Tugrik","Macanese Pataca","Mauritanian Ouguiya (pre-2018)","Mauritanian Ouguiya","Mauritian Rupee","Maldivian Rufiyaa","Malawian Kwacha","Mexican Peso","Malaysian Ringgit","Mozambican Metical","Namibian Dollar","Nigerian Naira","Nicaraguan CÃ³rdoba","Norwegian Krone","Nepalese Rupee","New Zealand Dollar","Omani Rial","Panamanian Balboa","Peruvian Nuevo Sol","Papua New Guinean Kina","Philippine Peso","Pakistani Rupee","Polish Zloty","Paraguayan Guarani","Qatari Rial","Romanian Leu","Serbian Dinar","Russian Ruble","Rwandan Franc","Saudi Riyal","Solomon Islands Dollar","Seychellois Rupee","Sudanese Pound","Swedish Krona","Singapore Dollar","Saint Helena Pound","Sierra Leonean Leone","Somali Shilling","SÃ£o TomÃ© and PrÃ­ncipe Dobra (pre-2018)","Thai Baht","Tajikistani Somoni","Turkmenistani Manat","Tunisian Dinar","Tongan Pa'anga","Turkish Lira","Trinidad and Tobago Dollar","New Taiwan Dollar","Tanzanian Shilling","Ukrainian Hryvnia","Ugandan Shilling","United States Dollar","Uruguayan Peso","Uzbekistan Som","Venezuelan BolÃ­var Soberano","Vietnamese Dong","Vanuatu Vatu","Samoan Tala","CFA Franc BEAC","Silver Ounce","Gold Ounce","East Caribbean Dollar","Special Drawing Rights","CFA Franc BCEAO","Palladium Ounce","CFP Franc","Platinum Ounce","Yemeni Rial","South African Rand","Zambian Kwacha","Zimbabwean Dollar","United Arab Emirates Dirham","Afghan Afghani","Albanian Lek","Armenian Dram","Netherlands Antillean Guilder","Angolan Kwanza","Argentine Peso","Australian Dollar","Aruban Florin","Azerbaijani Manat","Bosnia-Herzegovina Convertible Mark","Barbadian Dollar","Bangladeshi Taka","Bulgarian Lev","Bahraini Dinar","Burundian Franc","Bermudan Dollar","Brunei Dollar","Bolivian Boliviano","Brazilian Real","Bahamian Dollar","Bhutanese Ngultrum","Botswanan Pula","Belarusian Ruble","Belize Dollar","Canadian Dollar","Congolese Franc","Swiss Franc","Chilean Unit of Account (UF)","Chilean Peso","Chinese Yuan (Offshore)","Chinese Yuan","Colombian Peso","Costa Rican ColÃ³n","North Korean Won","South Korean Won","Kuwaiti Dinar","Cayman Islands Dollar","Kazakhstani Tenge","Laotian Kip","Surinamese Dollar","South Sudanese Pound","SÃ£o TomÃ© and PrÃ­ncipe Dobra","Salvadoran ColÃ³n","Syrian Pound","Swazi Lilangeni","Cuban Convertible Peso","Cuban Peso","Cape Verdean Escudo","Czech Republic Koruna","Djiboutian Franc","Danish Krone","Dominican Peso","Algerian Dinar","Egyptian Pound","Eritrean Nakfa","Ethiopian Birr","Euro","Fijian Dollar","Falkland Islands Pound","British Pound Sterling","Georgian Lari","Guernsey Pound","Ghanaian Cedi","Gibraltar Pound","Gambian Dalasi","Guinean Franc","Guatemalan Quetzal","Guyanaese Dollar","Hong Kong Dollar","Honduran Lempira","Croatian Kuna","Haitian Gourde","Hungarian Forint","Indonesian Rupiah","Israeli New Sheqel","Manx pound","Indian Rupee","Iraqi Dinar","Iranian Rial","Icelandic KrÃ³na","Jersey Pound","Jamaican Dollar","Jordanian Dinar","Japanese Yen","Kenyan Shilling","Kyrgystani Som","Cambodian Riel","Comorian Franc","Lebanese Pound","Sri Lankan Rupee","Liberian Dollar","Lesotho Loti","Libyan Dinar","Moroccan Dirham","Moldovan Leu","Malagasy Ariary","Macedonian Denar","Myanma Kyat","Venezuelan BolÃ­var Fuerte (Old)"};
    private boolean tapped1=false,tapped2=false;
    private CoordinatorLayout clayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inr=findViewById(R.id.inr);
        crypto=findViewById(R.id.crypto);
        select_coin=findViewById(R.id.select_coin);
        show=findViewById(R.id.show);
        fab=findViewById(R.id.fab);

        flipinr=findViewById(R.id.flipinr);
        flipusd=findViewById(R.id.flipusd);

        select_coin = findViewById(R.id.select_coin);
        clayout=findViewById(R.id.clayout);

        flipusd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(tapped1)
                {
                    if(containsDigit(crypto.getText().toString()))
                    {
                        Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
                        flipusd.startAnimation(aniRotateClk);

                        NumberFormat format = NumberFormat.getCurrencyInstance();
                        format.setMaximumFractionDigits(0);
                        format.setCurrency(Currency.getInstance("USD"));
                        String formattedString = format.format(usd);
                        formattedString = formattedString.substring(2, formattedString.length() - 0);
                        crypto.setText(formattedString);
                        tapped1 = false;
                    }
                    else
                    {
                        crypto.setText("DOLLAR");
                    }
                }
                else {

                    if(containsDigit(inr.getText().toString())) {

                        Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
                        flipusd.startAnimation(aniRotateClk);

                        crypto.setText(String.valueOf(usd));
                        tapped1 = true;
                    }
                }
            }
        });

        flipinr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tapped2)
                {
                    if(containsDigit(inr.getText().toString())) {

                        Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
                        flipinr.startAnimation(aniRotateClk);

                        NumberFormat format = NumberFormat.getCurrencyInstance();
                        format.setMaximumFractionDigits(0);
                        format.setCurrency(Currency.getInstance("INR"));
                        inr.setText(format.format(inr_rupee));
                        tapped2 = false;
                    }
                    else
                    {
                        inr.setText("RUPEE");
                    }
                }
                else {

                    if(containsDigit(inr.getText().toString())) {

                        Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
                        flipinr.startAnimation(aniRotateClk);

                        inr.setText(String.valueOf(inr_rupee));
                        tapped2 = true;
                    }
                }
            }
        });

        select_coin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkinfo = cm.getActiveNetworkInfo();

                if(networkinfo != null && networkinfo.isConnected()==true) {

                    new ApiProcess1().execute();
                    new ApiProcess2().execute();
                }
                else
                {
                   display_internet_label();
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkinfo = cm.getActiveNetworkInfo();

                if(networkinfo != null && networkinfo.isConnected()==true) {

                    Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise_fab);
                    fab.startAnimation(aniRotateClk);

                    new ApiProcess1().execute();
                    new ApiProcess2().execute();

                }
                else
                {
                    display_internet_label();
                }
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, names);

        select_coin.setThreshold(1);
        select_coin.setAdapter(arrayAdapter);
    }


    private void display_internet_label()
    {
        Snackbar.make(clayout, "No Internet", Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#000000"))
                .setAction("Action", null).setTextColor(Color.parseColor("#FFFFFF")).show();
    }

    public final boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }


    private int getNamePosition(String get_coin) {
        for(int j=0;j<names.length;j++)
        {
            if(get_coin.equals(names[j]))
            {
                name_pos=j;
            }
        }
        return name_pos;
    }


    class ApiProcess1 extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            String response = HttpRequest.excuteGet("https://api.happi.dev/v1/exchange?apikey=5641a3JTNIuBCWdeWFXrG1EJvsgF6Vf2bq5ANffO7i1fMdxuYytsN8HT");
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObj = new JSONObject(s);
                int length = jsonObj.getInt("length");

                if (length > 0) {

                    for (int i = 0; i < length; i++) {
                        JSONObject ans = jsonObj.getJSONArray("result").getJSONObject(i);

                        String code = ans.getString("code");
                        String name = ans.getString("name");

                        String get_coin = select_coin.getText().toString().trim();
                        if (!TextUtils.isEmpty(get_coin)) {
                            if (code.equals(codes[getNamePosition(get_coin)])) {
                                usd = ans.getDouble("price_usd");
                                crypto.setText(String.valueOf(usd));
                                tapped1=true;
                                tapped2=true;
                            }
                        }
                    }
                }

            } catch(Exception e) {
                Toast.makeText(MainActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ApiProcess2 extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String response = HttpRequest.excuteGet("https://api.happi.dev/v1/exchange/"+codes[getNamePosition(select_coin.getText().toString())]+"/INR?apikey=5641a3JTNIuBCWdeWFXrG1EJvsgF6Vf2bq5ANffO7i1fMdxuYytsN8HT");
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObj = new JSONObject(s);

                JSONObject ans = jsonObj.getJSONObject("result").getJSONObject("result");

                inr_rupee = ans.getDouble("value");

                inr.setText(String.valueOf(inr_rupee));
                tapped1=true;
                tapped2=true;

            }

            catch(Exception e) {
                Toast.makeText(MainActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}