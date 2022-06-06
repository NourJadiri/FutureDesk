#include<SPI.h>
#include<RF24.h>
#include "packet.h"

enum Colours {RED, GREEN, BLUE, CLEAR}; //Unumerate Colours RED,GREEN,BLUE and ClEAR
#define s2Pin 3 //S2 Colour Selection inputs define and connected to arduino pin 2
#define s3Pin 4 //S3 Colour Selection inputs define and connected to arduino pin 3
#define outPin 2  //Output define in Arduino pin 4

#define intensityPin A5

int send_time = 2;

void  print_packet(packet packet){
  for(int i=0;i<32;i++){
    Serial.print(packet.data[i]);
    if(i==31){
      Serial.print(".");
    } else {
      Serial.print(";");
    }
  }
  Serial.println("");
}


RF24 radio(9, 10);
//uint8_t address[6] = {122, 0xCE, 0xCC, 0xCE, 0xCC}; // Adresse du pipe
const uint64_t address= 0xB3B4B5B6CDLL;

void setup()
{
  Serial.begin(115200); //Serial begin at baudrate 9600
  pinMode(s2Pin, OUTPUT); //Pin Mode s2Pin as Output
  pinMode(s3Pin, OUTPUT); //Pin Mode s2Pin as Output
  pinMode(outPin, INPUT); //Pin Mode s2Pin as Input
  pinMode(intensityPin, INPUT);

  /**radio.begin();
  radio.setChannel(122);
  radio.setDataRate(RF24_2MBPS);

  radio.openWritingPipe(address);
  radio.stopListening();*/

}

void loop()
{

  packet packet_lumiere;

  unsigned long debut = millis();

  //Rempli le packet
  int i = 0;
  long somme[4] = {0, 0, 0, 0};
  int nbMesures = 0;
  Serial.print("Lumi:");

  while (i < 8) {
    //Capteur de couleur
    
    somme[0] += ReadColour(RED); //Read colors value to serial communication
    somme[1] += ReadColour(GREEN);
    somme[2] += ReadColour(BLUE);

    //Intensite lumineuse
    somme[3] += analogRead(intensityPin);
    nbMesures++;

    if (millis() - debut > send_time * 1000 / 8) {
      for (int j = 0; j < 3; j++) {
        packet_lumiere.data[4 * i + j] = somme[j] / nbMesures;
        somme[j]=0;
      }
      packet_lumiere.data[4 * i + 3] = map(somme[3] / nbMesures, 24, 700, 0, 255);
      somme[3]=0;
      i++;
      //Serial.println(nbMesures);
      nbMesures = 0;
      debut = millis();
      
    }
  }

  print_packet(packet_lumiere);
  
}

// Function reads colours and returns the a value betwenn 0 and 255
byte ReadColour(byte Colour)
{
  switch (Colour)
  {
    case RED:
      digitalWrite(s2Pin, LOW);
      digitalWrite(s3Pin, LOW);
      break;

    case GREEN:
      digitalWrite(s2Pin, HIGH);
      digitalWrite(s3Pin, HIGH);
      break;

    case BLUE:
      digitalWrite(s2Pin, LOW);
      digitalWrite(s3Pin, HIGH);
      break;

    case CLEAR:
      digitalWrite(s2Pin, HIGH);
      digitalWrite(s3Pin, LOW);
      break;
  }
  if (Colour == RED) {
    return map(pulseIn(outPin, LOW), 30, 2500, 255, 0); //PWM map to 30,2500,255,0
  }
  return map(pulseIn(outPin, HIGH), 30, 2500, 255, 0); //PWM map to 30,2500,255,0
}
