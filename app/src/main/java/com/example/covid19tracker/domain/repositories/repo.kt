import com.example.covid19tracker.domain.entities.CaseInfoClass
import com.example.covid19tracker.domain.requests.GetCasesRequest
import io.reactivex.Single

/** This is the innermost layer of the project and contains all the methods that have to be implemented
 * by class which implements the Repository contract.
 * The abstraction is done to let the developer implement the important contract methods' implementation as implementation may
 * vary according to individual business requirements.*/

interface Repository{
    fun getCasesInformation(getCasesRequest: GetCasesRequest):Single<CaseInfoClass>
}