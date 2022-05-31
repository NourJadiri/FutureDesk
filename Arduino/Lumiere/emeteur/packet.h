struct packet
{
    // We send 32 bytes.
    // Each bit correspond to the data of one flexiforce.
    // So we can have 64 measures in one packet
    byte data[32];
};
