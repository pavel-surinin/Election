/**
 * Created by nevyt on 2/21/2017.
 */
function getCountyInfo(self,id){
    axios
        .get("/county/" + id)
        .then(function(response){
        self.setState({
            county: response.data,
            isLoading: false,
        });
    })
}
function getCountyResults(self, id){
    axios.get('/results/county/' + id)
        .then(function(response){
            self.setState({
                results: response.data,
            });
        })
}
var CountyResultsContainer  = React.createClass({
    getInitialState: function() {
        return {
            results:[],
            county:[],
            isLoading:true,
        };
    },
    componentDidMount: function() {
        getCountyInfo(this,this.props.params.county);
        getCountyResults(this,this.props.params.county);

    },
    componentWillReceiveProps: function(nextProps) {
        getCountyInfo(this, nextProps.params.county);
        getCountyResults(this,this.props.params.county);
    },
    render: function() {
        console.log('this from countyresultscontainer:',this);
        if (this.state.isLoading) {
            return <div><img src='images/loading.gif'/></div>;
        }
        return (
            <CountyResultsComponent
                canvasBar={<canvas id='barC'></canvas>}
                county={this.state.county}
                countyId={this.props.params.county}
                results={this.state.results}
            />
        );
    }

});
window.CountyResultsContainer = CountyResultsContainer;