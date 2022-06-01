int ffs1 = A2; // FlexiForce sensor is connected analog pin A0 of arduino or mega.

int ffsdata = 0;
float vout;
void setup()
{
  Serial.begin(9600);
  pinMode(ffs1, INPUT);
}

void loop()
{
  ffsdata = analogRead(ffs1);
  //vout = (ffsdata * 5.0) / 1023.0;
  //vout = vout * cf ;
  Serial.print("Capteur de torsion: ");
  Serial.print(ffsdata);
  Serial.println("");
}
