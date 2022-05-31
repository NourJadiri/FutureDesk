#include <SPI.h>
#include <RF24.h>
#include "packet.h"

// Pin des capteurs de torsion
int pin[6] = {A0, A1, A2, A3, A4, A5};

// Seuils
int seuil[6] = {620, 620, 620, 620, 620, 620};

// Time between two sends
int send_time = 2;

RF24 radio(9, 10);
uint8_t address[6] = {100, 0xCE, 0xCC, 0xCE, 0xA3}; // Adresse du pipe
void setup()
{
    Serial.begin(115200);
    for (int i = 0; i < 6; i++)
    {
        pinMode(pin[i], INPUT);
    }

    radio.begin();
    radio.setChannel(122);
    radio.setDataRate(RF24_2MBPS);
    
    
    radio.openWritingPipe(address); // Ouvrir le Pipe en écriture
    radio.stopListening();
}

void loop()
{
    packet packet;

    unsigned long debut = millis();
    // Rempli le packet
    int i = 0;
    int nb_mesures=0;
    long somme[6]={0,0,0,0,0,0};

    // Donnees
    while (i <5 )
    {
      // Lecture des donnees
      for (int j = 0; j < 6; j++){
        somme[j] = somme[j]+analogRead(pin[j]);
      }
      nb_mesures++;

      if (millis() - debut > send_time * 1000/6){
          for (int j = 0; j < 6; j++){
            packet.data[j+6*i]=map(somme[j]/(nb_mesures),200,1023,0,255);
            somme[j]=0;
          }
          i++;
          nb_mesures = 0;
          debut=millis();
      }
    }

    // Reseau

    if (radio.write(&packet, sizeof(packet)))
    {
        Serial.println(F(" ... Ok ... "));
    }
    else
    {
        Serial.println(F(" ... failed ... "));
    }
}
