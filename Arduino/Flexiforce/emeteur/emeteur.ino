#include <SPI.h>
#include <RF24.h>
#include "packet.h"

// Pin dans l'ordre dd, da, gd, ga
int pin[4] = {A1, A2, A3, A4};

// Time between two sends
int send_time = 2;

RF24 radio(9, 10);
uint8_t address[6] = {122, 0xCE, 0xCC, 0xCE, 0xCC}; // Adresse du pipe

void setup()
{
  Serial.begin(115200);
  for (int i = 0; i < 4; i++)
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
  long somme[4] = {0, 0, 0, 0};
  int nbMesures = 0;

  // Donnees
  while (i < 8)
  {
    nbMesures++;
    for (int j = 0; j < 4; j++)
    {
      somme[j] += analogRead(pin[j]);
    }
    if (millis() - debut >= send_time * 1000 / 8)
    {
      for (int j = 0; j < 4; j++)
      {
        // Passer de 0-1023 a 0-255
        packet.data[j + 4 * i] = map(somme[j] / (nbMesures), 0, 1023, 0, 255);
        somme[j] = 0;
      }
      i++;
      nbMesures = 0;
      debut = millis();
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
