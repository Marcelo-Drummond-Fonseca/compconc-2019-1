public class Rooms {
    volatile int room[];
    public interface Handler {
        void onEmpty();
    }
    int size;
    public Rooms(int m) {
        this.size = m;
        for (int i=0; i<m;i++){
            room[i]=i;
        }
    }
    void enter(int i) { 
        while(1){
            if (room[i]!=i || NoThreads()){
                room[i]+=1;
                return;
            }
        }
    }
    boolean exit() { 
        int i = findOccupiedRoom();
        room[i]-=1;
        if (room[i]==i){
            this.setExitHandler(i, this.Handler);
            return true;
        }
        else{
            return false;
        }
    };
    public void setExitHandler(int i, Rooms.Handler h) { 
        
    }
    boolean NoThreads(){
        for (int i=0; i<this.size;i++){
            if(room[i]!=i){
                return false;
            }
        }
        return true;
    }
    int findOccupiedRoom(){
        for (int i=0; i<this.size;i++){
            if(room[i]!=i){
                return i;
            }
        }
    }
}