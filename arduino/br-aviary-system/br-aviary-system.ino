/* 
 *  Software Automacao
 *  Hardware: Arduino Uno R3
 *  Modo: Slave
 *  
 *  PROTOCOLO DE COMUNICACAO 
 *  Envio de dados e Recebimento de dados
 *
 *  Reles:
 *  1 - liga: a desliga: b
 *  2 - liga: c desliga: d
 *  3 - liga: e desliga: f
 *  4 - liga: g desliga: h
 */
 

//Bibliotecas coletar dados do Sensor de Temperatura
#include <OneWire.h>
#include <DallasTemperature.h>
//Biblioteca para comunicacao do Display LCD
#include <LiquidCrystal_I2C.h>

//Prototipo funcoes
void enderecoSensorTemp(DeviceAddress deviceAddress);
float getTemperatura();

void acionaLCD(float temperatura);


//Define a temperatura m�xima e m�nima para o range do sensor
#define TEMP_MAX  27
#define TEMP_MIN 23
#define TEMP_VENTILADOR 25
//Define a porta Digital onde o Botao esta conectado
#define botaoPause 7
//Define a porta Digital onde o Rele esta conectado
#define rele04 6
#define rele03 5
#define rele02 4
#define rele01 3
//Define o pino onde a sirene vai estar conectado
#define sireneInterna 9
// Porta do pino de sinal do DS18B20
#define sensorDigTemperatura 2
//Variavel de Temperatura
float temperatura = 0;
float tempMax = 0;
float tempMin = 0;

int dadoRecebido = 0;

//Variaveis dos Leds de Status
int ledTempOk = 11;
int ledTempNo = 10;
// Define o pino para conexao do Sensor
OneWire oneWire(sensorDigTemperatura);
//Display LCD - 16x2 - Utilizando modulo I2C
LiquidCrystal_I2C lcd(0x3F, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);

void setup() {
  //Inicializa comunica��o Serial
  Serial.begin(9600);
  
  //----------Configuracao do Display de LCD------------------//
  //---------------------------------------------------------//
  //Configura tamanho do Display
  lcd.begin(16, 2);
  //Pino Led Ok - Verde
  pinMode(ledTempOk, OUTPUT);
  //Pino Led No - Vermelho
  pinMode(ledTempNo, OUTPUT);
  //----------Configuracao do acionamento do Rele-------------//
  //---------------------------------------------------------//
  //Seta o pino indicado por rele como saida
  pinMode(rele01, OUTPUT);
  pinMode(rele02, OUTPUT);
  pinMode(rele03, OUTPUT);
  pinMode(rele04, OUTPUT);
  //Seta o pino indicado por botao Push como saida
  pinMode(botaoPause, OUTPUT);
  //Mantem rele desligado assim que iniciar o programa
  digitalWrite(rele01, HIGH);
  digitalWrite(rele02, HIGH);
  digitalWrite(rele03, HIGH);
  digitalWrite(rele04, HIGH);
  //Inicia Sirene Interna Desligada
  noTone(sireneInterna);
}

void loop() {

  //Receber dados
  if(Serial.available() > 0){

    dadoRecebido = Serial.read();
    if(dadoRecebido == 98){
      digitalWrite(rele01, HIGH);
    }else if(dadoRecebido == 97){
      digitalWrite(rele01, LOW);
    }else if(dadoRecebido == 100){
      digitalWrite(rele02, HIGH);
    }else if(dadoRecebido == 99){
      digitalWrite(rele02, LOW);
    }else if(dadoRecebido == 102){
      digitalWrite(rele03, HIGH);
    }else if(dadoRecebido == 101){
      digitalWrite(rele03, LOW);
    }else if(dadoRecebido == 104){
      digitalWrite(rele04, HIGH);
    }else if(dadoRecebido == 103){
      digitalWrite(rele04, LOW);
    }

    Serial.println(dadoRecebido);
  }
  
  float temperatura = getTemperatura();
  
  //Enviar Dados
  Serial.println(temperatura);
  int temperaturaInteira = (int) temperatura;

  acionaLCD(temperatura);
}
//Coleta dados Temperatura
float getTemperatura() {
  byte data[12];
  byte addr[8];
  if ( !oneWire.search(addr)) {
    //Nao ha mais sensores em cadeia entao, reseta.
    oneWire.reset_search();
    return -1000;
  }
  if ( OneWire::crc8( addr, 7) != addr[7]) {
    Serial.println("CRC is not valid!");
    return -1000;
  }
  if ( addr[0] != 0x10 && addr[0] != 0x28) {
    Serial.print("Device is not recognized");
    return -1000;
  }
  oneWire.reset();
  oneWire.select(addr);
  oneWire.write(0x44, 1); // Inicia a convers�o
  byte present = oneWire.reset();
  oneWire.select(addr);
  oneWire.write(0xBE); // Read Scratchpad
  for (int i = 0; i < 9; i++) { // Precisa-se de 9 Bytes
    data[i] = oneWire.read();
  }
  oneWire.reset_search();
  byte MSB = data[1];
  byte LSB = data[0];
  float tempRead = ((MSB << 8) | LSB); //using two's compliment
  float TemperaturaAtual = tempRead / 16;
  return TemperaturaAtual;
}

//ACIONA DISPLAY LCD OK
void acionaLCD(float temperatura) {
  
  lcd.setCursor(2, 0);
  lcd.print("TEMPERATURA");
  lcd.setCursor(4, 1);
  lcd.print(temperatura);
  lcd.print(" C");
}