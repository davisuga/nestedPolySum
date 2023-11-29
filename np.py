import numpy as np
from typing import List
import time 

def nested_sum_of_polynomial_np(a: int, b: int, k: int, f_coefs: List[float]) -> float:
    n = len(f_coefs) - 1
    coefs_vec = np.array([0, *f_coefs]).reshape(-1, 1)

    # calculating pascal triangle elements
    last_val = 1  # (b + k - a choose 0)
    for i in range(k):
        last_val *= (b + k - a - i) / (i + 1)

    # calculating base vector values
    base = [0, last_val]
    # (b + k - a choose k)
    for i in range(2, n + 2):
        base.append(base[i - 1] * (b + k + i - 1 - a) / (k + i - 1))
    base_vec = np.array(base).reshape(1, -1)

    # initializing omega matrix
    omega = np.zeros(shape=(n + 2, n + 2))
    omega[1, 1] = 1

    # calculating omega matrix
    for j in range(2, n + 2):
        for i in range(1, j + 1):
            omega[i, j] = (i - 1) * omega[i - 1, j - 1] - (i - a) * omega[i, j - 1]

    result = np.linalg.multi_dot([base_vec, omega, coefs_vec])
    return result[0, 0]


timeBefore3 = time.time()
result = nested_sum_of_polynomial_np(
    a=1,
    b=100,
    k=2000,
    f_coefs=[6e-40, 2e-40, -3e-40, -3e-40, -3e-40, 1e-40],  # Put your function coefficients here
)
timeAfter3 = time.time()
print(int(result))
print("Time alg3: ", (timeAfter3 - timeBefore3) * 1000, "ms")
from typing import List


def nested_sum_of_polynomial_pure(a: int, b: int, k: int, f_coefs: List[float]) -> float:
    mem = {}

    def func(x: int) -> int:
        # horner's method for polynomial evaluation
        acc = f_coefs[-1]
        for coef in f_coefs[-2::-1]:
            acc *= x
            acc += coef
        return acc

    def recursive_sum(x: int, k: int) -> float:
        if mem.get((x, k)) is not None:
            return mem[(x, k)]

        res = (
            func(x) if k == 0 else sum(recursive_sum(i, k - 1) for i in range(a, x + 1))
        )
        mem[(x, k)] = res
        return res

    return recursive_sum(b, k)

timeBefore = time.time()
result = nested_sum_of_polynomial_pure(
    a=1,
    b=100,
    k=2000,
    f_coefs=[6e-40, 2e-40, -3e-40, -3e-40, -3e-40, 1e-40],  # Put your function coefficients here
)
timeAfter = time.time()

print(result)
print("Time alg2: ", (timeAfter - timeBefore) * 1000, "ms")