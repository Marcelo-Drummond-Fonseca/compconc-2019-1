public class Main {
    private static int numPrisoners = 5;

    public static void main(String[] args) throws Exception {
        if (args.length >= 1)
            numPrisoners = Integer.parseInt(args[0]);

        System.out.println(numPrisoners);

        Prisoner[] prisoners = new Prisoner[numPrisoners];
        Room room = new Room();
        DoorClosed doorclosed = new DoorClosed();
        Warden warden = new Warden(room, prisoners, doorclosed);
        prisoners[0] = new Leader(0, room, warden, prisoners.length, doorclosed);
        for (int it = 1; it < prisoners.length; it++)
            prisoners[it] = new Prisoner(it, room, warden, doorclosed);

        warden.start();
        for (int it = 0; it < prisoners.length; it++)
            prisoners[it].start();

        warden.join();
        for (int it = 0; it < prisoners.length; it++)
            prisoners[it].join();
    }
}