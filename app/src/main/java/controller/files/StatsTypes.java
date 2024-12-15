package controller.files;

/**
 * Enum of all the stats types, with their description to show in the view
 * 
 * @author Emanuele Lamagna
 *
 */
public enum StatsTypes {

	RED("Red destroyed"), 
	YELLOW("Yellow destroyed"), 
	BLUE("Blue destroyed"), 
	PURPLE("Purple destroyed"), 
	GREEN("Green destroyed"), 
	ORANGE("Orange destroyed"), 
	FRECKLES("Freckles farmed"), 
	STRIPED("Striped farmed"), 
	WRAPPED("Wrapped farmed"), 
	CHOCOLATE("Chocolate destroyed"), 
	money("Total money"), 
	totalScore("Total score"), 
	level1Score("Level 1 top score"), 
	level2Score("Level 2 top score"), 
	level3Score("Level 3 top score"), 
	level4Score("Level 4 top score"), 
	level5Score("Level 5 top score"), 
	level6Score("Level 6 top score"), 
	level7Score("Level 7 top score"), 
	level8Score("Level 8 top score"), 
	level9Score("Level 9 top score"), 
	level10Score("Level 10 top score");
	
	private String description;
    
    private StatsTypes(final String desc){
        this.description = desc;
    }
    
    /**
     * Getter of the description of the element
     * 
     * @return the description of an element
     */
    public final String getDescription() {
        return this.description;
    }
	
}
