def digsum(x):
    _sum = 0
    while x != 0:
        _sum += x % 10
        x /= 10

    return digsum(_sum) if (_sum > 9) else _sum


for i in range(int(raw_input("enter the number\n"))):
    x = int(raw_input(i))
    print(digsum(x))


def min_packets(beans, n):
    import sys
    result = [sys.maxint] * (n + 1)
    result[0] = 0
    for bean in beans:
        for i in range(bean, n + 1):
            if 1 + result[i - bean] < result[i]:
                result[i] = 1 + result[i - bean]
    return result[n] if result[n] != sys.maxint else 'NO'


for i in range(int(raw_input())):
    in1 = map(int, raw_input().split(' '))
    print min_packets(sorted(map(int, raw_input().split(' '))), in1[0]) if in1[0] > 0 else 'NO'
    print min_packets([int(i) for i in raw_input().split(' ')], in1[0]) if in1[0] > 0 else 'NO'