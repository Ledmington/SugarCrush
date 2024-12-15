package model.game.grid.candies;

import java.util.Optional;

public final class CandyBuilderImpl implements CandyBuilder {

	private Optional<CandyColors> cndCol = Optional.empty();
	private Optional<CandyTypes> cndTyp = Optional.empty();
	
	private boolean built = false;
	
	@Override
	public final CandyBuilder setColor(final CandyColors cndCol) {
		if(this.built) {
			throw new IllegalStateException("Candy already built");
		}
		this.cndCol = Optional.of(cndCol);
		return this;
	}

	@Override
	public final CandyBuilder setType(final CandyTypes cndTyp) {
		if(this.built) {
			throw new IllegalStateException("Candy already built");
		}
		this.cndTyp = Optional.of(cndTyp);
		return this;
	}

	@Override
	public final Candy build() {
		if(this.built) {
			throw new IllegalStateException("Candy can't be built twice");
		}
		this.built = true;
		if(this.cndCol.isEmpty() || this.cndTyp.isEmpty()) {
			throw new IllegalStateException("Color and type must be set first");
		}
		return new Candy() {
			
			@Override
			public final CandyTypes getType() {
				return cndTyp.get();
			}
			
			@Override
			public final CandyColors getColor() {
				return cndCol.get();
			}
			
			@Override
			public final int hashCode() {
				final int prime = 31;
		        int result = 1;
		        result = prime * result + cndCol.hashCode();
		        result = prime * result + cndTyp.hashCode();
		        return result;
			}
			
			@Override
			public final boolean equals(final Object obj) {
				if(obj == null) {
					return false;
				}
				if(this.getClass() != obj.getClass()) {
					return false;
				}
				return cndCol.get() == ((Candy)obj).getColor() && cndTyp.get() == ((Candy)obj).getType();
			}
			
			@Override
			public String toString() {
				return cndTyp.get().name() + "_" + cndCol.get().name();
			}
		};
	}

}
