import sys
from Adafruit_IO import MQTTClient
import time
from uart import *
from Adafruit_IO import MQTTClient

AIO_FEED_IDs = ["dadn.cambien-anhsang", "dadn.cambien-doamdat", "dadn.cambien-nhietdo", 'dadn.cambien-nhietdo-doa',"dadn.led","dadn.maybom"]
AIO_USERNAME = "trungbui2405"
AIO_KEY = ""

def connected(client):
    print("Ket noi thanh cong ...")
    for topic in AIO_FEED_IDs:
        client.subscribe(topic)

def subscribe(client , userdata , mid , granted_qos):
    print("Subscribe thanh cong ...")

def disconnected(client):
    print("Ngat ket noi ...")
    sys.exit (1)

def message(client , feed_id , payload):
    print("Nhan du lieu: " + payload + " - feed_id: " + feed_id)
    if feed_id == "dadn.led":
        if payload == "0":
            writeData("0")
        else:
            writeData("1")
    elif feed_id == "dadn.maybom":
        if payload == "0":
            writeData("0")
        else:
            writeData("1")

client = MQTTClient(AIO_USERNAME , AIO_KEY)
client.on_connect = connected
client.on_disconnect = disconnected
client.on_message = message
client.on_subscribe = subscribe
client.connect()
client.loop_background()
counter = 10
while True:
    readSerial(client)
    time.sleep(1)