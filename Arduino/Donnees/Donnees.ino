int cycle=0;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
}

void  print_packet(int data[]){
  for(int i=0;i<32;i++){
    Serial.print(data[i]);
    Serial.print("; ");
  }
  Serial.println("");
}
void loop() {
  if(cycle==0){
  Serial.print("Flexi:");
  }
  else if(cycle==1){
  Serial.print("Torsi:");
  }
  else if(cycle==2){
  Serial.print("Lumi:");
  }
  int data[32];
  
  for(int i=0;i<32;i++){
    data[i]=random(0,255);
  }

  print_packet(data);
  cycle=(cycle+1)%3;
  delay(100);
}
