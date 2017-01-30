var ResultsListComponent = React.createClass({

  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading">
          <h3>Rezultatai</h3>
        </div>
        <div className="panel-body">
          <div className="tab-content">
            <ul className="nav nav-tabs">
              <li className="active"><a data-toggle="tab" href="#results0">Tvarka</a></li>
              <li><a data-toggle="tab" href="#results1"> Vienmandatė </a></li>
              <li><a data-toggle="tab" href="#results2"> Daugiamandatė </a></li>
            </ul>
              <div className="tab-content">
                <div id="results0" className="tab-pane fade in active">
                  <h3>Rezultatų patvirtinimo tvarka</h3>
                  <p>Rezultatai patvirtinami, tik tada</p>
                </div>
                <div id="results1" className="tab-pane fade">
                  <h3>Vienmandatės Rezultatai</h3>
                  <table width="100%" className="table table-striped table-bordered table-hover" id="dataTables-example">
                    <thead>
                      <tr>
                        <th> Apylinkės Numeris </th>
                        <th> Apylinkės Pavadinimas </th>
                        <th> Rezultatai pateikti </th>
                        <th> &nbsp; Veiksmai &nbsp; </th>
                      </tr>
                    </thead>
                    <tbody>
                      <ResultsSingleListRowComponent />
                    </tbody>
                  </table>
                </div>
                <div id="results2" className="tab-pane fade">
                  <h3>Daugiamandatės Rezultatai</h3>
                  <table width="100%" className="table table-striped table-bordered table-hover" id="dataTables-example">
                    <thead>
                      <tr>
                        <th> Apylinkės Numeris </th>
                        <th> Apylinkės Pavadinimas </th>
                        <th> Rezultatai pateikti </th>
                        <th> &nbsp; Veiksmai &nbsp; </th>
                      </tr>
                    </thead>
                    <tbody>
                      <ResultsMultiListRowComponent />
                    </tbody>
                  </table>
                </div>
              </div>
          </div>
        </div>
      </div>
    );
  }

});

window.ResultsListComponent = ResultsListComponent;
