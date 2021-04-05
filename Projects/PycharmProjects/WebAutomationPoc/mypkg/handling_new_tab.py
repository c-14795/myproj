from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys
import settings
from selenium import common

from mypkg.page_load import wait_for_page_load

browser = webdriver.Chrome()
browser.get("https://www.google.com")
element = browser.find_element_by_name("q")
element.send_keys("stackoverflow")

browser.find_element_by_name("btnK").click()
browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").send_keys(Keys.CONTROL,Keys.RETURN)
browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").send_keys(Keys.CONTROL,Keys.RETURN)
browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").send_keys(Keys.CONTROL,Keys.RETURN)
browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").send_keys(Keys.CONTROL,Keys.RETURN)

for i in browser.window_handles:
    browser.switch_to.window(i)


browser.find_element_by_xpath("//*[@id=\"question-summary-49967150\"]/div[2]/h3/a").click()

browser.close()