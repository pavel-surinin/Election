function mod(self){
  if (self.props.districtList.length % 50 > 0) {
    return 1;
  } else {
    return 0;
  }
}
﻿var DistrictListViewComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSuccesFixed(this.props.succesCreateText);
    var deleteDistrictMessage = alerts.showDangerFixed(this.props.deletedDistrictName);
    var self = this.props;
    var array = [];
    this.props.districtList.map(function(dist,index) {
      array.push(
        <DistrictListViewRowComponent
        id={dist.id}
        key={index}
        index={index}
        name={dist.name}
        adress={dist.adress}
        countyId={dist.countyId}
        countyName={dist.countyName}
        representativeId={dist.representativeId}
        representativeName={dist.representativeName}
        onHandleDelete={self.onHandleDelete}
        />
      );
    });
    var pagination = [];
    for (var i = 0; i < Math.round(this.props.districtsCount/100) + mod(this); i++) {
      if (i == this.props.currentPage) {
        pagination.push(<li className='active'><a href={'#/admin/district/?page=' + i}>{i+1}</a></li>);
      } else {
        pagination.push(<li><a href={'#/admin/district/?page=' + i}>{i+1}</a></li>);
      }
    }
    var alphabet = 'aąbcčdeęėfghiįyjklmnoprsštuųūvzž'.split('');
    var alphs = alphabet.map(function(letter){
      return <li><a href={'#/admin/district/?letter=' + letter}>{letter}</a></li>;
    });
    return (
          <div className='panel panel-default'>
            <div className='panel-heading' style={{paddingTop:20,paddingBottom:20}}>
              <h4 style={{display:'inline'}}><i className='fa fa-table'></i>
                &nbsp; Apylinkių sąrašas
              </h4>
              <div className='text-success pull-right'>
                <a href='#/admin/district/create' id='register-button' className='text-success'><i className='fa fa-plus'></i>
                &nbsp; Registruoti
                </a>
              </div>
            </div>
              <div className='panel-body'>
                {succesCreateMessage}
                {deleteDistrictMessage}
                <div className='paginatorCustom'>
                  <ul className="pagination pagination-sm">
                    {pagination}
                  </ul>
                </div>
                <div className='paginatorCustom'>
                  <ul className="pagination pagination-sm">
                    {alphs}
                  </ul>
                </div>
              </div>
                <table className='table table-striped table-hover'>
                <thead>
                <tr>
                  <th>
                    Eil. Nr.
                  </th>
                  <th>
                    Pavadinimas
                  </th>
                  <th>
                    Adresas
                  </th>
                  <th>
                    Apygarda
                  </th>
                  <th>
                    Atstovas
                  </th>
                  <th>
                    Veiksmai
                  </th>
                </tr>
                </thead>
                <tbody>
                  {array}
                </tbody>
              </table>
              <div className='paginatorCustom'>
                <ul className="pagination pagination-sm">
                  {pagination}
                </ul>
              </div>
              <div className='paginatorCustom'>
                <ul className="pagination pagination-sm">
                  {alphs}
                </ul>
              </div>
            </div>
    );
  }

});

window.DistrictListViewComponent = DistrictListViewComponent;
