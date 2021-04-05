# # def digsum(x):
# #     _sum = 0
# #     while x != 0:
# #         _sum += x % 10
# #         x /= 10
# #
# #     return digsum(_sum) if (_sum > 9) else _sum
# #
# #
# # def find_min_packets(beans, n):
# #     results = [0 for _ in range(n + 1)]
# #     for bean in range(1, n + 1):
# #         for i in range(len(beans)):
# #             print beans[i], bean
# #             if beans[i] <= bean:
# #                 res = results[bean - beans[i]]
# #                 if res == 0 and res + 1 < results[bean]:
# #                     print "HI"
# #                     results[bean] = res + 1
# #     print results
# #     return results[n]
# #
# #
# # def min_packets(beans,n):
# #     import sys
# #     result = [sys.maxint for _ in range(n+1)]
# #     result [0] = 0
# #     for bean in beans:
# #         for i in range(bean,n+1):
# #             if abs(i - bean) <= n:
# #                 if i >= bean:
# #                     if 1+result[i-bean] < result[i]:
# #                         result[i] = 1+result[i-bean]
# #     return result[n] if result[n] != sys.maxint else 'NO'
# #
# # # print min_packets([21,22,23,24],20)
# #
# # #a = [i for i in [21, 22, 23, 24] if i <= 0]
# # #print min_packets(['1','5','3','2','9'], 20) if 20 > 0 else 'NO'
# #
# # def min_packets_bak(beans, n):
# #     import sys
# #     result = [sys.maxint]*n+1
# #     result[0] = 0
# #     for bean in range(len(beans)):
# #         for i in range(beans[bean], len(result)):
# #             if abs(i - beans[bean]) <= n:
# #                 if i >= beans[bean]:
# #                     if 1 + result[i - beans[bean]] < result[i]:
# #                         result[i] = 1 + result[i - beans[bean]]
# #     return result[n] if result[n] != sys.maxint else 'NO'
# #
# # # for i in range(int(raw_input())):
# # #     in1 = raw_input().split(" ")
# # #     print(min_packets(map(int, raw_input().strip().split(' ')), int(in1[0])))
# # # [0, 1, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 2, 3, 2, 3, 3, 3, 2, 3, 3]
# # # [1, 2, 2, 2, 3, 2, 3, 3, 3, 2, 3, 3, 3, 4, 3, 4, 4, 4, 3, 4, 4]
# #
# # import time
# # import datetime
# # datetime.datetime.now()
# # import pymongo
# # pymongo.MongoClient()
# #
# # time.wait(123333)
# #
# #
# # """
# # Hi, How are you????/
# # How are you doing?
# # """
# #
# #
# # # for i in range(int(raw_input("enter the number\n"))):
# # #     x = int(raw_input(i))
# # #     print(digsum(x))
# #
# # import sys
# # res=[sys.maxint]*1000000000
# #
# # print len(res)
#
#
# def sample(func):
#     def wrapper(*args, **kwargs):
#         print("in decorator")
#         print("sdd", args, kwargs)
#         return func(*args, **kwargs)
#
#     return wrapper
#
#
# @sample
# def test_finc(a, b, c, d=None):
#     print("hello", a, b, c, d)
#
#
# test_finc(1, 23, 3, d="dd")
#
#
# def gen_test(a):
#     for i in range(a):
#         yield i
#
# for i in gen_test(356):
#     print(i)
#
