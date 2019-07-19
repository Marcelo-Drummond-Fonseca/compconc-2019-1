class Conta {
    private double saldo;
    private String nome;
    private int esperaComPreferencia;
    private Lock transacaoLock = new ReentrantLock();
    private Lock lock = new ReentrantLock();
    private final Condition fundoSuficienteCondicaoPrioridade = transacaoLock.newCondition();
    private final Condition fundoSuficientePrioridade = transacaoLock.newCondition();

    Conta(double initialsaldo) {
        setsaldo(initialsaldo);
    }

    Conta() {
        setsaldo(0.00);
    }

    void saque(boolean preferencia, double quantidade) throws InterruptedException {
        transacaoLock.lock();
        try {
            if (preferencia) {
                esperaComPreferencia++;
                while (saldo < quantidade) {
                    System.out.println(nome + "entrou na fila prioritaria");
                    fundoSuficienteCondicaoPrioridade.await();
                }
                esperaComPreferencia--;
                System.out.println(nome + "saiu da fila prioritaria");
                saldo -= quantidade;
                System.out.println(nome + ": saldo prioritario reduzido de " + quantidade + ", saldo total " + saldo );
                chamaOutraThread();
            } else {
                while (saldo < quantidade) {
                    System.out.println(nome + "entrou na fila comum");
                    fundoSuficientePrioridade.await();
                }
                saldo -= quantidade;
                System.out.println(nome + "saiu da fila comum");
                System.out.println(nome + ": saldo comum reduzido de " + quantidade + ", saldo total " + saldo );
                chamaOutraThread();
            }
        } finally {
            transacaoLock.unlock();
        }
    }

    
    void deposito(double quantidade) {
        transacaoLock.lock();
        try {
            saldo += quantidade;
            System.out.println(nome + ": saldo aumentado em " + quantidade + ", saldo total " + saldo );
            chamaOutraThread();
        } finally {
            transacaoLock.unlock();
        }
    }

    
    private void chamaOutraThread() {
        if (esperaComPreferencia == 0) {
            fundoSuficientePrioridade.signal();
        } else {
            fundoSuficienteCondicaoPrioridade.signal();
        }
    }

    
    private void chamaThreadEsperando() {
        if (esperaComPreferencia == 0) {
            fundoSuficientePrioridade.signalAll();
        } else {
            fundoSuficienteCondicaoPrioridade.signalAll();
        }
    }

    void transferencia(Conta origem, double quantidade, boolean preferencia) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("transferencia iniciada, vindo de "  origem.getnome() + " indo para " + nome + " , no valor de " + quantidade );
            origem.saque(preferencia, quantidade);
            deposito(quantidade);
            System.out.println("transferencia finalizada, vindo de "  origem.getnome() + " indo para " + nome + " , no valor de " + quantidade );
        } finally {
            lock.unlock();
        }
    }

    double getsaldo() {
        return saldo;
    }

    private void setsaldo(double saldo) {
        this.saldo = saldo;
    }

    private String getnome() {
        return nome;
    }

    void setnome(String nome) {
        this.nome = nome;
    }
}
