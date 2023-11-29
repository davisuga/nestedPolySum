const iter_lim = 10000;
const k_lim = 10000;

const MEM = Array.from(Array(iter_lim), () => new Array(k_lim));

function func(x) {
  return (
    6e-40 +
    2e-40 * x -
    3e-40 * x * x -
    3e-40 * x * x * x -
    3e-40 * x * x * x * x +
    1e-40 * x * x * x * x * x
  );
}

function memoizacao(a, x, k) {
  if (k === 0) {
    MEM[x][0] = func(x);
  } else if (x === a) {
    MEM[a][k] = MEM[a][k - 1];
  } else {
    MEM[x][k] = MEM[x - 1][k] + MEM[x][k - 1];
  }
}

function main(a, b, k) {
  for (let j = 0; j <= k; j++) {
    for (let i = a; i <= b; i++) {
      memoizacao(a, i, j);
    }
  }

  console.log(MEM[b][k]);
}

const a = parseInt(process.argv[2]);
const b = parseInt(process.argv[3]);
const k = parseInt(process.argv[4]);
main(a, b, k);

const before = Date.now();
main(a, b, k);
const after = Date.now();
console.log(`Tempo de execução: ${after - before}ms`);
