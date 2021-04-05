import time




class wait_for_page_load(object):
    def __init__(self, browser):
        self.browser = browser

    def __enter__(self):
        self.old_page = self.browser.find_element_by_tag_name('html')

    def page_has_loaded(self):
        new_page = self.browser.find_element_by_tag_name('html')
        print "iiii"
        return new_page.id != self.old_page.id

    def __exit__(self,*args,**kwargs):
        while not self.page_has_loaded():
            print "page not loaded yet..."
            self.page_has_loaded()


#
# def wait_for(condition_function):
#   start_time = time.time()
#   while time.time() < start_time + 3:
#     if condition_function():
#       return True
#     else:
#       time.sleep(0.1)
#   raise Exception(
#    'Timeout waiting for {}'.format(condition_function.**name**)
#   )