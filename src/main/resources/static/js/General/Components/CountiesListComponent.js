/**
 * Created by nevyt on 2/21/2017.
 */
var CountiesListComponent = React.createClass({

  render: function(){
    var countyInfo =this.props.countyList.map(function(county,index) {
      return(
          <tr>
            <td className="text-info"><a href={'#/county/'+ county.id}><i className="fa fa-angle-right" aria-hidden="true"></i> {county.name}</a></td>
            <td className="text-info"><i className="fa fa-cubes" aria-hidden="true"></i> {county.districtsCount}</td>
            <td className="text-info"><i className="fa fa-users" aria-hidden="true"></i> {county.votersCount}</td>
          </tr>
      );
    });
    return (
      <div>
        <div className="container">
          <h1 className='yellow'>Apygardų sąrašas</h1>
        </div>
        <div id="exTab1" className="container shadow">
          <div className="tab-content clearfix">
            <table width="100%" className="table table-striped table-hover" id="dataTables-example">
              <thead>
              <tr>
                <th> Apygarda </th>
                <th> Apylinkių skaičius</th>
                <th> Rinkejų skaičius</th>
              </tr>
              </thead>
              <tbody>
                {countyInfo}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
});
window.CountiesListComponent = CountiesListComponent;
