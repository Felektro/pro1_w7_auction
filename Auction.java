import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 7.0
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> listOfLots;
    // The number that will be given to the next lot entered into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        listOfLots = new ArrayList<>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        listOfLots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot aLot : listOfLots) {
            System.out.println(aLot.toString());
        }
    }
    
    public void close(){
        for(Lot lot : listOfLots){
            //System.out.println(lot.toString());
            boolean hasABid = !(lot.getHighestBid() == null);
            if(hasABid){
                System.out.println(lot.getHighestBid().getBidder() + " has won this item with a bid of " + lot.getHighestBid().getValue() + " dollars!");
            }else{
                System.out.println("No bid was made for this item");
            }
        }
    }
    
    public ArrayList<Lot> getUnsold(){
        ArrayList<Lot> unsoldLots = new ArrayList<>();
        
        for(Lot lot : listOfLots){
            boolean hasABid = !(lot.getHighestBid() == null);
            if(!hasABid){
                unsoldLots.add(lot);
            }
        }
        
        return unsoldLots;
    }

    
    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                                   lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                                   " already has a bid of: " +
                                   highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null if a lot with this 
     * number does not exist.
     * @param lotNumber The number of the lot to return.
     * @return The lot with the given number, or null.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = null;
            for(Lot lot : listOfLots){
                if(lotNumber == lot.getNumber()){
                     selectedLot = lot;
                     break;
                }
            }
            
            if(selectedLot == null){
                System.out.println("Lot number: " + lotNumber +
                               " does not exist.");
                return null;
            }
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                                   selectedLot.getNumber() +
                                   " was returned instead of " +
                                   lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                               " does not exist.");
            return null;
        }
    }
    
        /**
    * Remove the lot with the given lot number.
    * @param number The number of the lot to be removed.
    * @return The Lot with the given number, or null if
    * there is no such lot.
    */
    public Lot removeLot(int number){
        if((number >= 1) && (number < nextLotNumber)) {
            Lot selectedLot = null;
            for(Lot lot : listOfLots){
                if(number == lot.getNumber()){
                     selectedLot = lot;
                     listOfLots.remove(lot);
                     return selectedLot;
                }
            }
            return null;
        }
        else {
            System.out.println("Lot number: " + number +
                               " does not exist.");
            return null;
        }
    }
    
}

