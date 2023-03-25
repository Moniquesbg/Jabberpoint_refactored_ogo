import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String showTitle; //The title of the presentation
	private ArrayList<Slide> slides; //An ArrayList with slides
	private int currentSlideNumber; //The number of the current slide
	private SlideViewerComponent slideViewComponent; //The view component of the slides

	public Presentation() {
		this.slideViewComponent = null;
		this.slides = new ArrayList<>();
		this.currentSlideNumber = 0;
	}

	public int getSize() {
		return this.slides.size();
	}

	public String getTitle() {
		return this.showTitle;
	}

	public void setTitle(String nt) {
		this.showTitle = nt;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	//Returns the number of the current slide
	public int getSlideNumber() {
		return this.currentSlideNumber;
	}

	//Change the current slide number and report it the window
	public void setSlideNumber(int number) {

		//if slide number doesn't exist, stay on the slide number
		if (slides != null && slides.size() > number) {
			this.currentSlideNumber = number;
		}

		if (slideViewComponent != null) {
			this.slideViewComponent.update(this, getCurrentSlide());
		}
	}

	//Navigate to the previous slide unless we are at the first slide
	public void prevSlide() {
		if (this.currentSlideNumber > 0) {
			setSlideNumber(this.currentSlideNumber - 1);
	    }
	}

	//Navigate to the next slide unless we are at the last slide
	public void nextSlide() {
		if (this.currentSlideNumber < (this.slides.size()-1)) {
			setSlideNumber(this.currentSlideNumber + 1);
		}
	}

	//Remove the presentation
	void clear() {
		this.slides = new ArrayList<>();
		setSlideNumber(-1);
	}

	//Add a slide to the presentation
	public void append(Slide slide) {
		this.slides.add(slide);
	}

	//Return a slide with a specific number
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return this.slides.get(number);
	}

	//Return the current slide
	public Slide getCurrentSlide() {
		return getSlide(this.currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}