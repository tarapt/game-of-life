/**
 * @author TaraPrasad, Aneesh, Sourabh
 */
public interface SimulationControlsListener {

	void startButtonClicked();

	void pauseButtonClicked();

	void resumeButtonClicked();

	void restartButtonClicked();

	void nextGenerationButtonClicked();

	void clearButtonClicked();

	void removeCellsButtonClicked();

	void addCellsButtonClicked();
	
	void resetButtonClicked();
}
