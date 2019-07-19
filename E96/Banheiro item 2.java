public class Banheiro {
  volatile boolean estaAqui;
  volatile int qtdHomem,qtdMulher;
  Object lock;

  public Banheiro(){
    estaAqui = false;
    lock = new Object();
    qtdHomem = 0;
    qtdMulher = 0;
  }

  public void entraHomem(){
    synchronized (lock){
      try {
        while (estaAqui && (qtdMulher>0)) {lock.wait();}
      } catch (Exception e) {
        e.printStackTrace();
      } finally { 
        estaAqui = true; 
        qtdHomem++;
      }
    }
  }

  public void entraMulher(){
    synchronized (lock){
      try {
        while (estaAqui && (qtdHomem>0)) {lock.wait();}
      } catch (Exception e) {
        e.printStackTrace();
      } finally { 
        estaAqui = true;
        qtdMulher++;
      }
    }
  }

  public void saiHomem() {
    synchronized (lock){
      try{
        estaAqui = false;
        qtdHomem--;
        lock.notifyAll();
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }

  public void saiMulher() {
    synchronized (lock){
      try{
        estaAqui = false;
        qtdMulher--;
        lock.notifyAll();
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }

}
