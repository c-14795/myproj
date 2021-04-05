def print_smart_set(_list, len_of_list):
    _dict = {}
    _list.sort()
    final_val = []
    a =()
    a.index()
    for _ in range(len_of_list):
        bin_value = "{0:b}".format(int(_list[_])).replace('0', '')
        if len(_dict.setdefault(len(bin_value), [])) < 1:
            _dict.setdefault(len(bin_value), []).append(_list[_])
            final_val.append(_list[_])
    print  _dict
    # for j in _dict:
    #     final_val.append(_dict[j][0])
    # final_val.sort()
    for i in final_val:
        print i,
    print''


for i in range(int(raw_input(''))):
    len_of_list = int(raw_input(''))
    print_smart_set(map(int, raw_input('').split(' ')), len_of_list)
