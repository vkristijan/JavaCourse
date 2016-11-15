package hr.fer.android.jmbag0036483282;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A android page that displays two text boxes where the user should input numbers for calculation.
 * Supported operations are summation, subtraction, multiplication and division. The result will be
 * forwarded to DisplayActivity where it will be displayed.
 *
 * @author Kristijan Vulinovic
 */
public class CalculusActivity extends AppCompatActivity {
    /**
     * The radio button of the selected operator. The text on the radio button describes the
     * operation that should be performed.
     */
    private RadioButton selectedOperator;
    /**
     * The text field where the first operand is entered.
     */
    private EditText firstNumber;
    /**
     * The text field where the second operand is entered.
     */
    private EditText secondNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        Button btnOk = (Button)findViewById(R.id.operation);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CalculusActivity.this, DisplayActivity.class);
                String message = calculate();
                i.putExtra(DisplayActivity.MESSAGE_KEY, message);
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        firstNumber = (EditText)findViewById(R.id.firstNumber);
        secondNumber = (EditText)findViewById(R.id.secondNumber);

        if (resultCode == RESULT_OK){
            firstNumber.setText("");
            secondNumber.setText("");
        }
    }

    /**
     * Calculates the result of the operation, where the operand are written in the text boxes, and
     * the operation is defined with the selected radio button.
     *
     * @return a String representation of the operation result.
     */
    private String calculate(){
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int selectedOperationId = radioGroup.getCheckedRadioButtonId();
        selectedOperator = (RadioButton)findViewById(selectedOperationId);

        firstNumber = (EditText)findViewById(R.id.firstNumber);
        secondNumber = (EditText)findViewById(R.id.secondNumber);

        double first;
        double second;

        try {
            first = Double.parseDouble(firstNumber.getText().toString());
            second = Double.parseDouble(secondNumber.getText().toString());
        } catch (NumberFormatException exc){
            return generateError("Upisani podatci nisu brojevi.");
        }

        double result;

        switch (selectedOperator.getId()){
            case R.id.sumation:
                result = first + second;
                break;
            case R.id.subtraction:
                result = first - second;
                break;
            case R.id.multiplication:
                result = first * second;
                break;
            case R.id.division:
                if (second == 0){
                    return generateError("Nije moguće dijeliti s nulom.");
                }
                result = first / second;
                break;
            default:
                return generateError("Odabrana je nepostojeća računska operacija.");
        }


        StringBuilder sb = new StringBuilder();
        sb.append("Rezultat operacije ");
        sb.append(selectedOperator.getText());
        sb.append(" je ");
        if ((int)result == result){
            sb.append((int)result);
        } else {
            sb.append(String.format("%.4f", result));
        }
        return sb.toString();
    }

    /**
     * Generates an error message string that describes the error that occurred. The message will
     * contain the name of the operation that was performed, the operands, and a short description
     * of the message.
     *
     * @param msg
     *          a short textual description of the error.
     * @return s String representation of the whole error message.
     */
    private String generateError(String msg){
        StringBuilder sb = new StringBuilder();
        sb.append("Prilikom obavljanja operacije ");
        sb.append(selectedOperator.getText());
        sb.append(" nad unosima ");
        sb.append(firstNumber.getText().length() == 0 ? "blank" : firstNumber.getText() );
        sb.append(" i ");
        sb.append(secondNumber.getText().length() == 0 ? "blank" : secondNumber.getText());
        sb.append(" došlo je do sljedeće greške: ");
        sb.append(msg);

        return sb.toString();
    }
}
