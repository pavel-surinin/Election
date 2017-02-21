//function getCandidatesListTable(self){
//  var candidateTable = [];
//    candidateTable = this.props.candidateList.map(function(cand,index){
//      return (
//        <tr key={index} className='small'>
//          <td>{this.props.name}</td>
//          <td>{this.props.surname}</td>
//          <td>{this.props.birthDate}</td>
//          <td>{this.props.partijosPavadinimas}</td>
//          <td>{this.props.numberInParty}</td>
//          <td>{this.props.countyName}</td>
//        </tr>
//      );
//    });
//  return candidateTable;
//}


var CandidatesList  = React.createClass({

    componentDidMount: function(){
        $(document).ready(function() {
          $('#searchable-table').DataTable({
            responsive: true
          });
        });
    },

  render: function() {

//    var candidateTable = getCandidatesListTable(this);
    var candidates = [];
        this.props.candidateList.map(function(cand,index) {
          candidates.push(
            <CandidatesListRowComponent
              id={cand.id}
              key={index}
              name={cand.name}
              surname={cand.surname}
              birthDate={cand.birthDate}
              partijosId={cand.partijosId}
              partijosPavadinimas={cand.partijosPavadinimas}
              countyId={cand.countyId}
              countyName={cand.countyName}
              description={cand.description}
              numberInParty={cand.numberInParty}
            />
          );
        });

    return (

          <div>
              <div className="container"><h1>Lietuvos Respublikos Seimo rinkimuose dalyvaujančių asmenų sąrašas</h1></div>
                 <div className="panel panel-default">
                    <div className="panel-body">
                       <table className="table table-striped" id="searchable-table">
                          <thead>
                             <tr>
                                <th>Vardas</th>
                                <th>Pavardė</th>
                                <th>Gimimo data</th>
                                <th>Partija</th>
                                <th>Numeris partijos sąraše</th>
                                <th>Vienmandatė apygarda</th>
                             </tr>
                          </thead>
                          <tbody>
                            {candidates}
                          </tbody>
                       </table>
                              <script src="../vendor/jquery/jquery.min.js"></script>

                              <script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

                              <script src="../vendor/metisMenu/metisMenu.min.js"></script>

                              <script src="../vendor/datatables/js/jquery.dataTables.min.js"></script>
                              <script src="../vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
                              <script src="../vendor/datatables-responsive/dataTables.responsive.js"></script>

                              <script src="../dist/js/sb-admin-2.js"></script>

                    </div>
                 </div>
              </div>

    );
  },
});

window.CandidatesList = CandidatesList;
