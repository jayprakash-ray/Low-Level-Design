// Full refactored elevator system with floor bound checks and demo runner

import java.util.*;
// === Core Interfaces and Enums ===
enum Direction {
    UP, DOWN, IDLE
}

enum RequestType {
    PICKUP_UP, PICKUP_DOWN, DESTINATION
}

// === Core Classes ===
class Elevator {
    private int currentFloor;
    private Direction direction;
    private final HashSet<Request> requests;

    public Elevator() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.requests = new HashSet<>();
    }

    public int addRequest(Request request) {
        if (request.getFloor() < 0 || request.getFloor() > 10) {
            return -1;
        }
        if(this.getCurrentFloor() == request.getFloor()) return 0;

        this.requests.add(request);
        return 1;
    }

    public void step() {
        if(requests.size() == 0) 
            return;

        //if idle serve the nearest request
        if(this.direction == Direction.IDLE)
        {
            int minDistance = Integer.MAX_VALUE;
            Request nearest = null;

            for(var req : requests)
            {
                int dis = Math.abs(req.getFloor() - currentFloor);
                if(dis < minDistance || (dis == minDistance && (nearest == null || nearest.getFloor() < currentFloor )))
                {
                    minDistance = dis;
                    nearest = req;
                }
            }
            
            if(currentFloor < nearest.getFloor()) 
            {
                this.direction = Direction.UP;
            }
            else
            {
                this.direction = Direction.DOWN;
            }
        }
        else 
        {
            RequestType pickupType = (direction == Direction.UP) ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
            Request pickupRequest = new Request(currentFloor, pickupType);
            Request destinationRequest = new Request(currentFloor, RequestType.DESTINATION);

            if (requests.contains(destinationRequest) || requests.contains(pickupRequest)) {
                requests.remove(pickupRequest);
                requests.remove(destinationRequest);

                if (requests.size() == 0) {
                    direction = Direction.IDLE;
                    return;
                }
            }
            if (!hasMoreRequestAhead(direction)) {
                direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
                return;
            }

            if (direction == Direction.UP) {
                currentFloor++;
            } else if (direction == Direction.DOWN) {
                currentFloor--;
            }
            
        }
    }

    boolean hasMoreRequestAhead(Direction dir) {
         for (Request request : requests) {
            if (dir == Direction.UP && request.getFloor() > currentFloor) {
                return true;
            }
            if (dir == Direction.DOWN && request.getFloor() < currentFloor) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasRequestsAtOrBeyond(int floor, Direction dir) {
        for (Request request : requests) {
            if (dir == Direction.UP && request.getFloor() >= floor) {
                if (request.getRequestType() == RequestType.PICKUP_UP || request.getRequestType() == RequestType.DESTINATION) {
                    return true;
                }
            }
            if (dir == Direction.DOWN && request.getFloor() <= floor) {
                if (request.getRequestType() == RequestType.PICKUP_DOWN || request.getRequestType() == RequestType.DESTINATION) {
                    return true;
                }
            }
        }
        return false;
    }

    public void show() {
        System.out.println("  Floor: " + currentFloor + " | Direction: " + direction + " | Requests: " + requests);
    }

    public Direction getDirection() { return direction; }
    public int getCurrentFloor() { return currentFloor; }
    public HashSet<Request> getRequests() { return new HashSet<>(requests); }
}

class Request {
    private final int floor;
    private final RequestType requestType;

    public Request(int floor, RequestType requestType) {
        this.floor = floor;
        this.requestType = requestType;
    }

    public int getFloor() { return floor; }
    public RequestType getRequestType() { return requestType; }

    @Override
    public String toString() {
        return "Request(" + floor + ", " + requestType + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Request)) return false;
        Request other = (Request) obj;
        return this.floor == other.floor && this.requestType == other.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, requestType);
    }
}

class ElevatorController {
    private List<Elevator> elevators;

    public ElevatorController() {
        elevators = new ArrayList<>();
        elevators.add(new Elevator());
        elevators.add(new Elevator());
        elevators.add(new Elevator());
    }

    public boolean requestElevator(int floor, Direction direction) {
        if (floor < 0 || floor > 9) {
            return false;
        }
        if (direction != Direction.UP && direction != Direction.DOWN) {
            return false;
        }

        Elevator best = selectBestElevator(floor, direction);
        if (best == null) {
            return false;
        }
        RequestType type = (direction == Direction.UP) ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
        int status = best.addRequest(new Request(floor, type));
        if(status > 0) {
            return true;
        }
        return false;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    private Elevator selectBestElevator(int floor, Direction direction) {
        Elevator best = findCommittedToFloor(floor, direction);
        if (best != null) {
            return best;
        }

        best = findNearestIdle(floor);
        if (best != null) {
            return best;
        }

        return findNearest(floor);
    }

    private Elevator findCommittedToFloor(int floor, Direction direction) {
        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getDirection() != direction) {
                continue;
            }

            boolean isMovingToward =
                (direction == Direction.UP && e.getCurrentFloor() < floor) ||
                (direction == Direction.DOWN && e.getCurrentFloor() > floor);

            if (!isMovingToward) {
                continue;
            }

            if (!e.hasRequestsAtOrBeyond(floor, direction)) {
                continue;
            }

            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }

        return nearest;
    }


    private Elevator findNearestIdle(int floor) {
        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getDirection() != Direction.IDLE) {
                continue;
            }

            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }

        return nearest;
    }

    private Elevator findNearest(int floor) {
        Elevator nearest = elevators.get(0);
        int minDistance = Math.abs(elevators.get(0).getCurrentFloor() - floor);

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }

        return nearest;
    }
}


// === Demo Runner ===
class ElevatorSystemDemo {
    public static void main(String[] args) {
        ElevatorController controller = new ElevatorController();
        Scanner scanner = new Scanner(System.in);
        int tick = 0;

        System.out.println("=== Elevator System Simulator ===");
        System.out.println("Commands:");
        System.out.println("  request <floor> <direction>     - Request elevator (direction: UP/DOWN)");
        System.out.println("  destination <elevator> <floor>  - Set destination in elevator (elevator: 1/2/3)");
        System.out.println("  tick                            - Advance one tick");
        System.out.println("  status                          - Show current status");
        System.out.println("  exit                            - Exit simulator\n");

        showStatus(controller, tick);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            if (command.equals("destination") && parts.length == 3) {
                try {
                    int elevatorId = Integer.parseInt(parts[1]) - 1;
                    int floor = Integer.parseInt(parts[2]);
                    List<Elevator> elevators = controller.getElevators();

                    if (elevatorId < 0 || elevatorId >= elevators.size()) {
                        System.out.println("Invalid elevator ID. Use 1, 2, or 3.");
                        continue;
                    }

                    Elevator elevator = elevators.get(elevatorId);
                    int status = elevator.addRequest(new Request(floor, RequestType.DESTINATION));
                    if (status > 0) {
                        System.out.println("Destination added to Elevator " + (elevatorId + 1) + ": Floor " + floor);
                        showStatus(controller, tick);
                    } else if (status == 0) {
                        System.out.println("Elevator " + (elevatorId + 1) + " is already at floor " + floor + ".");
                    } else {
                        System.out.println("Failed: Invalid floor (must be 0-10).");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format. Use: destination <elevator> <floor>");
                }

            } else if (command.equals("request") && parts.length == 3) {
                try {
                    int floor = Integer.parseInt(parts[1]);
                    Direction direction = Direction.valueOf(parts[2].toUpperCase());

                    if (direction == Direction.IDLE) {
                        System.out.println("Invalid direction. Use UP or DOWN.");
                        continue;
                    }

                    boolean success = controller.requestElevator(floor, direction);
                    if (success) {
                        System.out.println("Request added: Floor " + floor + " " + direction);
                        showStatus(controller, tick);
                    } else {
                        System.out.println("Failed to add request. Invalid floor or direction.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid floor number.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction. Use UP or DOWN.");
                }

            } else if (command.equals("tick")) {
                controller.step();
                tick++;
                System.out.println("\n--- Tick " + tick + " ---");
                showStatus(controller, tick);

            } else if (command.equals("status")) {
                showStatus(controller, tick);

            } else if (command.equals("exit")) {
                System.out.println("Exit simulator.");
                break;

            } else {
                System.out.println("Unknown command. Try 'request', 'tick', 'status', or 'exit'.");
            }
        }

        scanner.close();
    }

    private static void showStatus(ElevatorController controller, int tick) {
        System.out.println("\n[Tick " + tick + "]");
        List<Elevator> elevators = controller.getElevators();
        for (int i = 0; i < elevators.size(); i++) {
            Elevator elevator = elevators.get(i);
            System.out.println("Elevator " + (i + 1) + ": Floor=" + elevator.getCurrentFloor() + 
                             " Direction=" + elevator.getDirection() + 
                             " Requests=" + elevator.getRequests());
        }
        System.out.println();
    }
}
