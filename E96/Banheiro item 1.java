public class Banheiro {
  Lock lock;
  Condition condicao;
  int qtdHomem, qtdMulher;

  public Banheiro() {
    lock = new ReentrantLock();
    condicao = lock.newCondition();
    qtdHomem = 0;
    qtdMulher = 0;
  }

  public void entraHomem() {
    try{
      lock.lock();
      try {
        while (qtdMulher>0) {condicao.wait();}
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      qtdHomem++;
      System.out.println("Homens: "+qtdHomem );
    } finally {
      lock.unlock();
    }
  }

  public void entraMulher() {
    try{
      lock.lock();
      try {
        while (qtdHomem>0) {
          condicao.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      qtdMulher++;
      System.out.println("Mulheres: "+qtdMulher);
    } finally{
      lock.unlock();
    }
  }

  public void saiHomem(){
    try{
      lock.lock();
      qtdHomem--;
      condicao.signalAll();
    }finally{
      lock.unlock();
    }
  }

  public void saiMulher(){
    try{
      lock.lock();
      qtdMulher--;
      condicao.signalAll();
    }finally{
      lock.unlock();
    }
  }

}
