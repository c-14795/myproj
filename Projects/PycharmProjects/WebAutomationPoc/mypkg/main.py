import csv

import time
import calendar

from selenium.common.exceptions import NoSuchElementException
from selenium import webdriver
from selenium.webdriver.support.ui import Select
import settings

month_names = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'September', 'October', 'November',
               'December']

try:
    browser = webdriver.Chrome()
    browser.get("https://www.rahasyavedicastrology.com/rva-software/")
    browser.switch_to.frame(browser.find_element_by_xpath("//*[@id=\"form-iframe\"]"))
    name_element = browser.find_element_by_xpath("//*[@id=\"m-name\"]")
    day_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-date\"]"))
    month_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-month\"]"))
    year_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-year\"]"))
    hour_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-hour\"]"))
    minute_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-minute\"]"))
    second_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-seconds\"]"))
    country_of_birth_element = Select(browser.find_element_by_xpath("//*[@id=\"m-countryCode\"]"))
    place_of_birth = browser.find_element_by_xpath("//*[@id=\"m-hr-place\"]")

    language = Select(browser.find_element_by_xpath("//*[@id=\"hr-language\"]"))
    chart_type = Select(browser.find_element_by_xpath("//*[@id=\"chart-type\"]"))

    csv_file = open('/home/c14795/Documents/test_input.csv', mode='r')

    csv_reader = csv.DictReader(csv_file)

    for row in csv_reader:
        print  row

        name_element.clear()

        name_element.send_keys(row['Name'])
        day_of_birth_element.select_by_visible_text(row['DOB'].split(" ")[0].split("/")[0])
        month_of_birth_element.select_by_visible_text(month_names[int(row['DOB'].split(" ")[0].split("/")[1])])
        year_of_birth_element.select_by_visible_text(row['DOB'].split(" ")[0].split("/")[2])
        minute_of_birth_element.select_by_visible_text(row['DOB'].split(" ")[1].split(":")[1])
        hour_of_birth_element.select_by_visible_text(row['DOB'].split(" ")[1].split(":")[0])
        second_of_birth_element.select_by_visible_text("0")
        country_of_birth_element.select_by_visible_text(row['Country'])
        place_of_birth.clear()
        place_of_birth.send_keys(row['Place'])
        place_of_birth_results = browser.find_elements_by_xpath("//*[@id=\"results\"]")
        browser.find_element_by_xpath("//*[@id=\"m-advanced-geo-option\"]").click()
        latitude = browser.find_element_by_xpath("//*[@id=\"m-hr-lat\"]")
        longitude = browser.find_element_by_xpath("//*[@id=\"m-hr-lon\"]")
        latitude.clear()
        longitude.clear()
        latitude.send_keys(row['Latitude'])
        longitude.send_keys(row['Longitude'])
        language.select_by_visible_text("English")
        chart_type.select_by_visible_text("South Indian Style")

        browser.find_element_by_xpath("//*[@id=\"m-submit-hr-form\"]").click()

        '''Approach 1'''
        elemd = None
        while True:
            try:
                elemd = browser.find_element_by_xpath("//*[@id=\"kpData\"]/div[1]/div/div[1]/div/table")
                print "hello found it", elemd.id
                break
            except NoSuchElementException:
                print "Page has not been loaded yet, retrying to find the elem.. after 30 seconds"
                time.sleep(5)

        row_in_csv = {}
        row_in_csv['Name'] = row['Name']
        row_in_csv['DOB'] = row['DOB']
        row_in_csv['Country'] = row['Country']
        row_in_csv['Latitude'] = row['Latitude']
        row_in_csv['Place'] = row['Place']
        House_sign = 'House{}_Sign'
        Planet_sign = 'Planet{}_Sign'
        House_star = 'House{}_Star'
        Planet_star = 'Planet{}_Star'
        counter = 0
        for output_row in elemd.find_elements_by_tag_name("tr")[1:]:
            counter = counter + 1
            cols = output_row.find_elements_by_tag_name("td")
            # print cols
            if len(cols) > 0:
                #       print cols[0].text, cols[2].text
                row_in_csv[House_sign.format(counter)] = cols[0].text
                row_in_csv[House_star.format(counter)] = cols[2].text
        elem2 = browser.find_element_by_xpath("//*[@id=\"kpData\"]/div[1]/div/div[2]/div/table")

        counter_planet = 0
        for output_row in elem2.find_elements_by_tag_name("tr")[1:]:
            cols = output_row.find_elements_by_tag_name("td")
            counter_planet = counter_planet + 1
            # print cols
            if len(cols) > 0:
                #        print cols[0].text, cols[3].text
                row_in_csv[Planet_sign.format(counter_planet)] = cols[0].text
                row_in_csv[Planet_star.format(counter_planet)] = cols[3].text
        print row_in_csv

        headers = row_in_csv.keys()
        headers.sort()
        import os

        init = os.path.exists("test.csv")
        with open("test.csv", "a+") as csv_file:
            writer = csv.DictWriter(csv_file, fieldnames=headers)
            if not init:
                writer.writeheader()
            writer.writerow(row_in_csv)

    # #stars = row.find_elements_by_tag_name("td")[2]
    #
    # for i in row.find_elements_by_tag_name("td"):
    #     print  i.text,i.tag_name

    # browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").click()
    browser.close()
    '''Appoach 2'''
    #
    # my_page2 = browser.find_element_by_tag_name("html")
    # while my_page2.id == my_page.id:
    #     my_page2 = browser.find_element_by_tag_name("html")
    #     print my_page2.id,my_page.id


    # elem1 = browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a")
    # elem1.click()
    # browser.close()


    # with wait_for_page_load(browser):
    #     browser.find_element_by_xpath("//*[@id=\"rso\"]/div[1]/div/div/div/div/h3/a").click()
except :
    browser.close()