var CandidatesListViewComponent = React.createClass({

	render: function() {
	    var array = [];
	    this.props.candidateList.map(function(cand,index) {
	      array.push(
	        <CandidatesListViewRowComponent
	          id={cand.id}
	          key={index}
	          name={cand.name}
	          surname={cand.surname}
	          birthDate={cand.birthDate}
	          partijosId={cand.partijosId}
	          partijosPavadinimas={cand.partijosPavadinimas}
	          description={cand.description}
						numberInParty={cand.numberInParty}
	        />
	      );
	    });

    return (
			    <div className="panel panel-default">
					<div className="panel-heading">
							<h4><i className="fa fa-table"></i>&nbsp; Kandidatu sąrašas</h4>
					</div>
					<div className="panel-body">
					</div>
            <table width="100%" className="table table-striped" id="dataTables-example">
             <thead>
              <tr>
	              <th>Nr.</th>
	              <th>Vardas</th>
	              <th>Pavardė</th>
	              <th>Gimimo data</th>
	              <th>Partija</th>
								<th>Veiksmai</th>
              </tr>
            </thead>
            <tbody>
			  			{array}
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

    );
}
});

window.CandidatesListViewComponent = CandidatesListViewComponent;
