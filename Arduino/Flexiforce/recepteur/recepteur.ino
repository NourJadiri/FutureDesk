#include <SPI.h>
#include <RF24.h>
#include "packet.h"

RF24 radio(9, 10);
uint8_t address_flexiforce[6] = {122, 0xCE, 0xCC, 0xCE, 0xCC}; // Adresse du pipe
const uint64_t address_torsion=0xB3B4B5B6F1LL;
const uint64_t address_lumiere= 0xB3B4B5B6CDLL;
int rcv_val = 0;

packet recv;

void  print_packet(packet packet){
  for(int i=0;i<32;i++){
    Serial.print(packet.data[i]);
    if(i<31)
      Serial.print(";");
  }
  Serial.println(".");
}

void setup()
{
    Serial.begin(115200); // Initialiser la communication série

    radio.begin();
    radio.setChannel(122);
    radio.setDataRate(RF24_2MBPS);

    radio.openReadingPipe(0, address_flexiforce);
    radio.openReadingPipe(1, address_torsion);
    radio.openReadingPipe(2, address_lumiere);
    radio.startListening();
}

void loop(void)
{
    unsigned long wait = micros();
    boolean timeout = false;

    byte pipe_nb;
    while (radio.available(&pipe_nb))
    {
      
        radio.read(&recv, sizeof(packet));
        //Flexiforce
        if(pipe_nb==0){
          Serial.print("Flexi:");
        }
        else if(pipe_nb==1){
          Serial.print("Torsi:");
        }
        else if(pipe_nb==2){
          Serial.print("Lumi:");
        }
        print_packet(recv);
    }
}
