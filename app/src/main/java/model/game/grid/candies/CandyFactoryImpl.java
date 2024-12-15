package model.game.grid.candies;

/**
 * 
 * @author Filippo Benvenuti
 *
 */
public final class CandyFactoryImpl implements CandyFactory{
	
	public Candy getNormalCandy(final CandyColors cndColor) {
		if(cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}
		
		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.NORMAL)
				.build();
	}
	
	public final Candy getVerticalStripedCandy(final CandyColors cndColor) {
		if(cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}
		
		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_VERTICAL)
				.build();
	}
	
	public final Candy getHorizontalStriped(final CandyColors cndColor) {
		if(cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}
		
		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_HORIZONTAL)
				.build();
	}
	
	public final Candy getWrapped(final CandyColors cndColor) {
		if(cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}
		
		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.WRAPPED)
				.build();
	}
	
	public final Candy getFreckles() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.FRECKLES)
				.setType(CandyTypes.FRECKLES)
				.build();
	}

	public final Candy getChocolate() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.CHOCOLATE)
				.setType(CandyTypes.CHOCOLATE)
				.build();
	}
}
