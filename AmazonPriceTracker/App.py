import requests
from bs4 import BeautifulSoup
import smtplib
import time


URL = 'https://www.amazon.com/Hanes-EcoSmart-Fleece-Sweatshirt-Small/dp/B072K68D77/ref=zg_bs_fashion_home_2?_encoding=UTF8&psc=1&refRID=X5M0527QKQB6MBXKKGEY'

headers = {
    "User-Agent":'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36'
}


def check_price():
    page = requests.get(URL, headers=headers)

    soup = BeautifulSoup(page.content, 'html.parser')
    #print(soup.prettify())

    title = soup.find(id="productTitle").get_text()
    print(title.strip())

    price = soup.find(id="priceblock_ourprice").get_text()
    print (price.strip())

    converted_price = float(price[1:])
    print (converted_price)

    if (converted_price < 12.00):
        send_email()

def send_email():
    #Establish connection with gmail servers
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.ehlo()
    server.starttls()
    server.ehlo()

    #Individual Login, replace stings with username and App password
    server.login('email@gmail.com', 'password')

    subject = 'Desired item is on sale!'
    body = 'Check the amazon link https://www.amazon.com/Hanes-EcoSmart-Fleece-Sweatshirt-Small/dp/B072K68D77/ref=zg_bs_fashion_home_2?_encoding=UTF8&psc=1&refRID=X5M0527QKQB6MBXKKGEY'

    msg = f"Subject: {subject}\n\n{body}"

    server.sendmail(
        'email@gmail.com',
        'second_email@gmail.com',
        msg
    )
    print("Email has been sent")
    server.quit()

while(True):
    check_price()
    #Check once a day as long as the script is running
    time.sleep(86400)